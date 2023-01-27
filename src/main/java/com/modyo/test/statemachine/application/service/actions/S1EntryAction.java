package com.modyo.test.statemachine.application.service.actions;

import com.modyo.ms.commons.core.exceptions.TechnicalErrorException;
import com.modyo.test.statemachine.config.statemachine.AbstractAction;
import com.modyo.test.statemachine.domain.model.Solicitud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class S1EntryAction extends AbstractAction<Solicitud> {

  @Override
  public void execute(StateContext<String, String> context) {
    var entity = this.getEntity(context);
    if (entity == null) {
      log.info("Action: Wrong transition?");
      throw new TechnicalErrorException("Entity not found", "");
    } else {
      log.info("Action: Entity {} entering to state {}", entity.getId(), this.getTarget(context));
    }
  }
}
