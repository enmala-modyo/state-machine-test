package com.modyo.ms.commons.statemachine.components;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.guard.Guard;

class StateMachineComponentsCatalogTest {

  private final HashMap<String, Action<String, String>> actions = new HashMap<>();

  private final HashMap<String, Guard<String, String>> guards = new HashMap<>();

  private final StateMachineComponentsCatalog stateMachineComponentsCatalog = new StateMachineComponentsCatalog(actions,guards);

  @BeforeEach
  void setUp() {

  }

  @Test
  void testGetAction_NotFound_DoNothing() {
    assertDoesNotThrow(() -> stateMachineComponentsCatalog.getAction("Name").execute(mock(StateContext.class)));
  }

  @Test
  void testGetGuard_NotFound_ReturnsFalse() {
    assertFalse(stateMachineComponentsCatalog.getGuard("Name").evaluate(mock(StateContext.class)));
  }
}

