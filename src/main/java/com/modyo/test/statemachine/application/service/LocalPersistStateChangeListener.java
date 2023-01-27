package com.modyo.test.statemachine.application.service;

import com.modyo.test.statemachine.application.port.out.SaveSolicitudPort;
import com.modyo.test.statemachine.config.statemachine.AbstractPersistStateChangeListener;
import com.modyo.test.statemachine.domain.model.Solicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalPersistStateChangeListener extends AbstractPersistStateChangeListener<Solicitud> {

  @Autowired
  private SaveSolicitudPort savePort;

  @Override
  protected void saveEntity(Solicitud entity) {
    this.savePort.save(entity);
  }

  @Override
  protected void setState(Solicitud entity, String state) {
    entity.setState(state);
  }
}
