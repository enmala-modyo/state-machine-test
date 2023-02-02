package com.modyo.test.statemachine.application.service.guards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.modyo.test.statemachine.config.StateMachineConfig;
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
  void testConstructor() {
    assertEquals(StateMachineConfig.SM_ENTITY_HEADER, (new S3Guard()).getEntityHeaderName());
  }

  @Test
  void testEvaluate_AllwaysTrue() {
    assertTrue(s3Guard.evaluate(mock(StateContext.class)));
  }

}

