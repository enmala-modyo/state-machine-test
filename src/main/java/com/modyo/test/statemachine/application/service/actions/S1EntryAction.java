package com.modyo.test.statemachine.application.service.actions;

import com.modyo.ms.commons.core.exceptions.TechnicalErrorException;
import com.modyo.ms.commons.statemachine.generic.AbstractAction;
import com.modyo.test.statemachine.application.port.out.LoadSolicitudPort;
import com.modyo.test.statemachine.config.StateMachineConfig;
import com.modyo.test.statemachine.domain.model.Estado;
import com.modyo.test.statemachine.domain.model.Evento;
import com.modyo.test.statemachine.domain.model.Solicitud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component("s1EntryAction")
@Slf4j
public class S1EntryAction extends AbstractAction<Solicitud, Estado, Evento> {

  @Autowired
  private LoadSolicitudPort loadSolicitudPort;

  @Override
  public void execute(StateContext<Estado, Evento> context) {
    var entity = this.getEntity(context);
    if (entity == null) {
      log.info("Action: Wrong transition?");
      throw new TechnicalErrorException("Entity not found", "");
    } else {
      loadSolicitudPort.load(entity.getId());
      log.info("Action: Entity {} entering to state {}", entity.getId(), this.getTarget(context));
    }
  }

  @Override
  protected String getEntityHeaderName() {
    return StateMachineConfig.SM_ENTITY_HEADER;
  }
}
