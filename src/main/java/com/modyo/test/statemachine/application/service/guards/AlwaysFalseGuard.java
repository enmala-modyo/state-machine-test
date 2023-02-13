package com.modyo.test.statemachine.application.service.guards;

import com.modyo.ms.commons.statemachine.generic.AbstractGuard;
import com.modyo.test.statemachine.domain.model.Estado;
import com.modyo.test.statemachine.domain.model.Evento;
import com.modyo.test.statemachine.domain.model.Solicitud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component("alwaysFalseGuard")
@Slf4j
public class AlwaysFalseGuard extends AbstractGuard<Solicitud, Estado, Evento> {

  @Override
  public boolean evaluate(StateContext<Estado, Evento> context) {
    return false;
  }
}
