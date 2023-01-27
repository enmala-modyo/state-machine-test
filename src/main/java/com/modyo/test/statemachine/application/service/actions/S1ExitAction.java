package com.modyo.test.statemachine.application.service.actions;

import com.modyo.ms.commons.core.exceptions.TechnicalErrorException;
import com.modyo.test.statemachine.config.statemachine.AbstractAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class S1ExitAction extends AbstractAction {

  @Override
  public void execute(StateContext<String, String> context) {
    var entity = this.getSolicitud(context);
    if (entity == null) {
      log.info("Action: Wrong transition?");
      throw new TechnicalErrorException("Entity not found", "");
    } else {
      log.info("Action: Entity {} leaving the state {}", entity.getId(), this.getSource(context));
    }
  }
}
