package com.modyo.ms.commons.statemachine.components;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.guard.Guard;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StateMachineComponentsCatalog.class})
@ExtendWith(SpringExtension.class)
class StateMachineComponentsCatalogTest {

  @MockBean
  private Action<String, String> action;

  @MockBean
  private Guard<String, String> guard;

  @Autowired
  private Map<String, Action<String, String>> actions;

  @Autowired
  private Map<String, Guard<String, String>> guards;

  @Autowired
  private StateMachineComponentsCatalog stateMachineComponentsCatalog;

  @Test
  void testGetAction_NotFound_DoNothing() {
    assertDoesNotThrow(()->stateMachineComponentsCatalog.getAction("Name").execute(mock(StateContext.class)));
  }

  @Test
  void testGetGuard_NotFound_ReturnsFalse() {
    assertFalse(stateMachineComponentsCatalog.getGuard("Name").evaluate(mock(StateContext.class)));
  }
}

