package com.modyo.test.statemachine.application.service;

import static com.modyo.test.statemachine.config.StateMachineConfig.SM_ENTITY_HEADER;

import com.modyo.test.statemachine.application.port.in.SolicitudUseCase;
import com.modyo.test.statemachine.application.port.out.CreateSolicitudPort;
import com.modyo.test.statemachine.application.port.out.LoadSolicitudPort;
import com.modyo.test.statemachine.domain.model.Solicitud;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SolicitudUseCaseService implements SolicitudUseCase {

  private final PersistStateMachineHandler stateMachineHandler;
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
    sendEvent(loadPort.loadAndLock(solicitudId), eventName);
    return loadPort.load(solicitudId);
  }

  private void sendEvent(Solicitud solicitud, String event) {
    log.info("sending event to statemachine: {} {}", solicitud.getId(), event);
    stateMachineHandler.handleEventWithStateReactively(MessageBuilder.withPayload(event)
            .setHeader(SM_ENTITY_HEADER, solicitud)
            .build(), solicitud.getState())
        .subscribe();
  }

}
