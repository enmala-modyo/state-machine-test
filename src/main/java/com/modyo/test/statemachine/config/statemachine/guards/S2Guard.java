package com.modyo.test.statemachine.config.statemachine.guards;

import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class S2Guard implements Guard<String, String> {

  private final SecureRandom random = new SecureRandom();

  @Override
  public boolean evaluate(StateContext<String, String> context) {
    var result = random.nextBoolean();
    log.info("Resultado de la evaluación: " + result);
    return result;
  }
}
