package com.modyo.test.statemachine.application.service;

import static com.modyo.test.statemachine.config.StateMachineConfig.SM_ENTITY_HEADER;

import com.modyo.test.statemachine.application.port.out.SaveSolicitudPort;
import com.modyo.ms.commons.statemachine.generic.AbstractPersistStateChangeListener;
import com.modyo.test.statemachine.domain.model.Solicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalPersistStateChangeListener extends AbstractPersistStateChangeListener<Solicitud> {

  @Autowired
  private SaveSolicitudPort savePort;

  @Override
  protected String getEntityHeaderName() {
    return SM_ENTITY_HEADER;
  }

  @Override
  protected void saveEntity(Solicitud entity) {
    this.savePort.save(entity);
  }

  @Override
  protected void setState(Solicitud entity, String state) {
    entity.setState(state);
  }
}
