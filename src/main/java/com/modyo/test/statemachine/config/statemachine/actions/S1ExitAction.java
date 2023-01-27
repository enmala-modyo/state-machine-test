package com.modyo.test.statemachine.config.statemachine.actions;

import static com.modyo.test.statemachine.config.statemachine.StateMachineConfig.SM_ENTITY_HEADER;

import com.modyo.ms.commons.core.exceptions.TechnicalErrorException;
import com.modyo.test.statemachine.domain.model.Solicitud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class S1ExitAction implements Action<String, String> {

  @Override
  public void execute(StateContext<String, String> context) {
    var entity = (Solicitud) context.getMessageHeader(SM_ENTITY_HEADER);
    if (entity == null) {
      log.info("Action: Wrong transition?");
      throw new TechnicalErrorException("Entity not found", "");
    } else {
      log.info("Action: Entity {} leaving the state {}", entity.getId(), context.getTransition().getSource().getId());
    }
  }
}
