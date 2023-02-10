package com.modyo.test.statemachine.application.service;

import com.modyo.ms.commons.statemachine.generic.AbstractStateMachinePersistInterceptor;
import com.modyo.test.statemachine.application.port.out.SaveSolicitudPort;
import com.modyo.test.statemachine.domain.model.Estado;
import com.modyo.test.statemachine.domain.model.Evento;
import com.modyo.test.statemachine.domain.model.Solicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StateChangePersistenceInterceptor
    extends AbstractStateMachinePersistInterceptor<Solicitud, Estado, Evento> {

  @Autowired
  private SaveSolicitudPort savePort;

  @Override
  protected void saveEntity(Solicitud entity) {
    this.savePort.save(entity);
  }

  @Override
  protected void updateState(Solicitud entity, Estado state) {
    entity.setState(state);
  }
}
