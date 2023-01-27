package com.modyo.test.statemachine.config.statemachine;

import static com.modyo.test.statemachine.config.statemachine.StateMachineConfig.SM_ENTITY_HEADER;

import com.modyo.test.statemachine.application.port.out.SaveSolicitudPort;
import com.modyo.test.statemachine.domain.model.Solicitud;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler.PersistStateChangeListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocalPersistStateChangeListener implements PersistStateChangeListener {
  private final SaveSolicitudPort savePort;

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void onPersist(State<String, String> state, Message<String> message,
      Transition<String, String> transition, StateMachine<String, String> stateMachine) {
    if (message != null && message.getHeaders().containsKey(SM_ENTITY_HEADER)) {
      Solicitud solicitud = message.getHeaders().get(SM_ENTITY_HEADER, Solicitud.class);
      if(solicitud!=null) {
        log.info("Updating Solicitud state: {} from {} to {}", solicitud.getId(), solicitud.getState(), state.getId());
        solicitud.setState(state.getId());
        savePort.save(solicitud);
      }
    }
  }

}
