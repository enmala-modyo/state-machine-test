package com.modyo.test.statemachine.application.service;

import static com.modyo.test.statemachine.config.StateMachineConfig.SM_ENTITY_HEADER;

import com.modyo.ms.commons.statemachine.generic.AbstractStateMachinePersistInterceptor;
import com.modyo.test.statemachine.application.port.out.SaveSolicitudPort;
import com.modyo.test.statemachine.domain.model.Estado;
import com.modyo.test.statemachine.domain.model.Evento;
import com.modyo.test.statemachine.domain.model.Solicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalPersistStateChangeInterceptor
    extends AbstractStateMachinePersistInterceptor<Solicitud, Estado, Evento> {

  @Autowired
  private SaveSolicitudPort savePort;

  @Override
  public String getEntityHeaderName() {
    return SM_ENTITY_HEADER;
  }

  @Override
  protected void saveEntity(Solicitud entity) {
    this.savePort.save(entity);
  }

  @Override
  protected void updateState(Solicitud entity, Estado state) {
    entity.setState(state);
  }
}
