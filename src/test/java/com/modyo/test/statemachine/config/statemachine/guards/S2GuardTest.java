package com.modyo.test.statemachine.config.statemachine.guards;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {S2Guard.class})
@ExtendWith(SpringExtension.class)
class S2GuardTest {

  @Autowired
  private S2Guard s2Guard;

  @Test
  void testEvaluate() {
    StateContext<String, String> context = mock(StateContext.class);
    assertDoesNotThrow(()->this.s2Guard.evaluate(context));
  }
}

