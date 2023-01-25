package com.modyo.test.statemachine.application.service;

import static com.modyo.test.statemachine.config.statemachine.StateMachineConfig.SM_ENTITY_HEADER;

import com.modyo.test.statemachine.application.port.in.SolicitudUseCase;
import com.modyo.test.statemachine.application.port.out.CreateSolicitudPort;
import com.modyo.test.statemachine.application.port.out.LoadSolicitudPort;
import com.modyo.test.statemachine.config.statemachine.EventsEnum;
import com.modyo.test.statemachine.config.statemachine.StatesEnum;
import com.modyo.test.statemachine.domain.model.Solicitud;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SolicitudUseCaseService implements SolicitudUseCase {

  private final StateMachineFactory<StatesEnum, EventsEnum> stateMachineFactory;
  private final SolicitudStateChangeInterceptor interceptor;
  private final LoadSolicitudPort loadPort;
  private final CreateSolicitudPort createPort;

  @Override
  public List<Solicitud> getAll() {
    return loadPort.loadAllActive();
  }

  @Override
  public Solicitud getOne(Long idSolicitud) {
    return loadPort.load(idSolicitud);
  }

  @Override
  public Solicitud newSolicitud(String name) {
    return createPort.create(name);
  }

  @Transactional
  @Override
  public Solicitud processEvent(Long solicitudId, String eventName) {
    sendEvent(solicitudId, EventsEnum.valueOf(eventName));
    return loadPort.load(solicitudId);
  }

  private void sendEvent(Long solicitudId, EventsEnum event) {
    Solicitud solicitud = loadPort.load(solicitudId);
    var sm = build(solicitud);
    Message<EventsEnum> msg = MessageBuilder.withPayload(event)
        .setHeader(SM_ENTITY_HEADER, solicitudId)
        .build();
    sm.sendEvent(Mono.just(msg));
  }

  private StateMachine<StatesEnum, EventsEnum> build(Solicitud solicitud) {
    StateMachine<StatesEnum, EventsEnum> sm = stateMachineFactory.getStateMachine(Long.toString(solicitud.getId()));
    sm.stopReactively().block();
    sm.getStateMachineAccessor()
        .doWithAllRegions(sma -> {
          sma.addStateMachineInterceptor(interceptor);
          sma.resetStateMachineReactively(
              new DefaultStateMachineContext<>(StatesEnum.valueOf(solicitud.getState()), null,
                  null, null));
        });
    sm.startReactively().block();
    return sm;
  }
}
