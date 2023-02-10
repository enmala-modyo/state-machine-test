package com.modyo.test.statemachine.application.service.actions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.modyo.ms.commons.core.exceptions.TechnicalErrorException;
import com.modyo.test.statemachine.domain.model.Estado;
import com.modyo.test.statemachine.domain.model.Evento;
import com.modyo.test.statemachine.domain.model.Solicitud;
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
    StateContext<Estado, Evento> context = mock(StateContext.class);
    assertThrows(TechnicalErrorException.class, () -> this.s1ExitAction.execute(context));
  }

  @Test
  void testExecute_WithContext() {
    Transition<Estado, Evento> transition = mock(Transition.class);
    State<Estado, Evento> state = mock(State.class);
    when(state.getId()).thenReturn(Estado.S1);
    when(transition.getSource()).thenReturn(state);
    StateContext<Estado, Evento> context = mock(StateContext.class);
    when(context.getMessageHeader(anyString())).thenReturn(new Solicitud());
    when(context.getTransition()).thenReturn(transition);
    assertDoesNotThrow(() -> this.s1ExitAction.execute(context));
  }
}
