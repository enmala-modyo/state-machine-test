package com.modyo.test.statemachine.config.statemachine.guards;

import com.modyo.test.statemachine.config.statemachine.EventsEnum;
import com.modyo.test.statemachine.config.statemachine.StatesEnum;
import java.security.SecureRandom;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Component
public class S2Guard implements Guard<StatesEnum, EventsEnum> {

  private final SecureRandom random = new SecureRandom();

  @Override
  public boolean evaluate(StateContext<StatesEnum, EventsEnum> context) {
    return random.nextBoolean();
  }
}
