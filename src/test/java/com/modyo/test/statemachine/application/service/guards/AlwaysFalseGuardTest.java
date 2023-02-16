package com.modyo.test.statemachine.application.service.guards;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.statemachine.StateContext;

class AlwaysFalseGuardTest {

  private final AlwaysFalseGuard s3Guard = new AlwaysFalseGuard();

  @Test
  void testEvaluate_AllwaysReturnsFalse() {
    assertFalse(s3Guard.evaluate(mock(StateContext.class)));
  }

}

