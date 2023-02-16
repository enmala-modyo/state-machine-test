package com.modyo.test.statemachine.application.service.guards;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    when(adapter.getAnswer()).thenReturn(true);
  }

  @Test
  void testEvaluate() {
    StateContext<Estado, Evento> context = mock(StateContext.class);
    when(context.getMessageHeader(anyString())).thenReturn(new Solicitud());
    assertDoesNotThrow(() -> this.guard.evaluate(context));
  }
}

