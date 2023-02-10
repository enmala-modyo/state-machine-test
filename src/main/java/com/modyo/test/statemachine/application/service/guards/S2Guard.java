package com.modyo.test.statemachine.application.service.guards;

import com.modyo.ms.commons.statemachine.generic.AbstractGuard;
import com.modyo.test.statemachine.domain.model.Estado;
import com.modyo.test.statemachine.domain.model.Evento;
import com.modyo.test.statemachine.domain.model.Solicitud;
import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component("s2Guard")
@Slf4j
public class S2Guard extends AbstractGuard<Solicitud, Estado, Evento> {

  private final SecureRandom random = new SecureRandom();

  @Override
  public boolean evaluate(StateContext<Estado, Evento> context) {
    var result = random.nextBoolean();
    log.info("Solicitud {} - rule evaluation: {}", this.getEntity(context).getId(), result);
    return result;
  }

}
