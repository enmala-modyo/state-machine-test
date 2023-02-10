package com.modyo.test.statemachine.application.service.guards;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {S3Guard.class})
@ExtendWith(SpringExtension.class)
class S3GuardTest {

  @Autowired
  private S3Guard s3Guard;

  @Test
  void testEvaluate_AllwaysReturnsFalse() {
    assertFalse(s3Guard.evaluate(mock(StateContext.class)));
  }

}

