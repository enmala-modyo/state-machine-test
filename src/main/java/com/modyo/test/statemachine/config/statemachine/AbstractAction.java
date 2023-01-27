package com.modyo.test.statemachine.config.statemachine;

import static com.modyo.test.statemachine.config.statemachine.StateMachineConfig.SM_ENTITY_HEADER;

import com.modyo.test.statemachine.domain.model.Solicitud;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public abstract class AbstractAction implements Action<String, String> {

  protected Solicitud getSolicitud(StateContext<String, String> context){
    return (Solicitud) context.getMessageHeader(SM_ENTITY_HEADER);
  }

  protected String getSource(StateContext<String, String> context) {
    return context.getTransition().getSource().getId();
  }

  protected String getTarget(StateContext<String, String> context) {
    return context.getTransition().getTarget().getId();
  }
}
