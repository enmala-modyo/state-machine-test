package com.modyo.test.statemachine.config.statemachine.actions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.modyo.ms.commons.core.exceptions.TechnicalErrorException;
import com.modyo.test.statemachine.config.statemachine.EventsEnum;
import com.modyo.test.statemachine.config.statemachine.StatesEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {S1ExitAction.class})
@ExtendWith(SpringExtension.class)
class S1ExitActionTest {

  @Autowired
  private S1ExitAction s1ExitAction;
  @Test
  void testExecute_WithNoContext() {
    StateContext<StatesEnum, EventsEnum> context = mock(StateContext.class);
    assertThrows(TechnicalErrorException.class,()->this.s1ExitAction.execute(context));
  }

  @Test
  void testExecute_WithContext() {
    Transition<StatesEnum, EventsEnum> transition = mock(Transition.class);
    State<StatesEnum, EventsEnum> state = mock(State.class);
    when(state.getId()).thenReturn(StatesEnum.S1);
    when(transition.getSource()).thenReturn(state);
    StateContext<StatesEnum, EventsEnum> context = mock(StateContext.class);
    when(context.getMessageHeader(anyString())).thenReturn(1L);
    when(context.getTransition()).thenReturn(transition);
    assertDoesNotThrow(()->this.s1ExitAction.execute(context));
  }
}
