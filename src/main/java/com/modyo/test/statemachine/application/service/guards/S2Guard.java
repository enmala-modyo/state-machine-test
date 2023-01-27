package com.modyo.test.statemachine.application.service.guards;

import com.modyo.test.statemachine.config.statemachine.AbstractGuard;
import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class S2Guard extends AbstractGuard {

  private final SecureRandom random = new SecureRandom();

  @Override
  public boolean evaluate(StateContext<String, String> context) {
    var result = random.nextBoolean();
    log.info("Solicitud {} - resultado: {}", this.getSolicitud(context).getId(), result);
    return result;
  }
}
