package com.modyo.ms.commons.statemachine.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.state.ObjectState;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DefaultStateChangeLoggerListener.class})
@ExtendWith(SpringExtension.class)
class DefaultStateChangeLoggerListenerTest {

  @Autowired
  private DefaultStateChangeLoggerListener stateChangeLoggerListener;

  @Test
  void testStateChanged() {
    ObjectState<Object, Object> objectState = new ObjectState<>("Id", new ArrayList<>());

    ArrayList<Object> objectList = new ArrayList<>();
    ObjectState<Object, Object> objectState1 = new ObjectState<>("Id", objectList);

    stateChangeLoggerListener.stateChanged( objectState, objectState1);
    assertFalse(objectState.isAutoStartup());
    assertEquals(objectList, objectState.getTriggers());
    assertEquals(0, objectState.getPhase());
    assertFalse(objectState1.isAutoStartup());
    Collection<Object> expectedTriggers = objectState.getDeferredEvents();
    assertEquals(expectedTriggers, objectState1.getTriggers());
    assertEquals(0, objectState1.getPhase());
  }

  @Test
  void testStateChanged2() {
    ObjectState<Object, Object> objectState = new ObjectState<>("Id", new ArrayList<>());

    stateChangeLoggerListener.stateChanged(null, objectState);
    assertFalse(objectState.isAutoStartup());
    Collection<Object> expectedTriggers = objectState.getDeferredEvents();
    assertEquals(expectedTriggers, objectState.getTriggers());
    assertEquals(0, objectState.getPhase());
  }

  @Test
  void testStateChanged3() {
    ObjectState<Object, Object> objectState = (ObjectState<Object, Object>) mock(ObjectState.class);
    when(objectState.getId()).thenReturn("Id");
    stateChangeLoggerListener.stateChanged(objectState, new ObjectState<>("Id", new ArrayList<>()));
    verify(objectState).getId();
  }

}

