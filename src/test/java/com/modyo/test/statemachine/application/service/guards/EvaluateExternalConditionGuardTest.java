package com.modyo.test.statemachine.application.service.guards;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.modyo.test.statemachine.application.port.out.LoadRandomAnswerPort;
import com.modyo.test.statemachine.domain.model.Estado;
import com.modyo.test.statemachine.domain.model.Evento;
import com.modyo.test.statemachine.domain.model.Solicitud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.statemachine.StateContext;

@ExtendWith(MockitoExtension.class)
class EvaluateExternalConditionGuardTest {

  @Mock
  LoadRandomAnswerPort adapter;
  @InjectMocks
  private EvaluateExternalConditionGuard guard;

  @BeforeEach
  void setup(){

  }

  @Test
  void testEvaluateTrue() {
    when(adapter.getAnswer()).thenReturn(true);
    StateContext<Estado, Evento> context = mock(StateContext.class);
    when(context.getMessageHeader(anyString())).thenReturn(new Solicitud());
    assertTrue(this.guard.evaluate(context));
  }

  @Test
  void testEvaluateFalse() {
    when(adapter.getAnswer()).thenReturn(false);
    StateContext<Estado, Evento> context = mock(StateContext.class);
    when(context.getMessageHeader(anyString())).thenReturn(new Solicitud());
    assertFalse(this.guard.evaluate(context));
  }

}

