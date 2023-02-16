package com.modyo.test.statemachine.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.modyo.test.statemachine.domain.model.Estado;
import com.modyo.test.statemachine.domain.model.Evento;
import com.modyo.test.statemachine.log.LogCapture;
import com.modyo.test.statemachine.log.LogCaptureExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.messaging.Message;
import org.springframework.statemachine.state.State;

@ExtendWith(LogCaptureExtension.class)
class StateChangeLoggerTest {

  private final StateChangeLogger logger = new StateChangeLogger();

  Message<Evento> message = mock(Message.class);
  State<Estado,Evento> state = mock(State.class);

  @BeforeEach
  void setUp() {
  }

  @Test
  void eventNotAccepted(LogCapture logCapture) {
    logger.eventNotAccepted(message);
    assertEquals(1, logCapture.getLoggingEvents().size());
  }

  @Test
  void stateChanged(LogCapture logCapture) {
    logger.stateChanged(state, state);
    assertEquals(2, logCapture.getLoggingEvents().size());
    assertEquals("Event Accepted", logCapture.getLoggingEventAt(1).getMessage());
  }
}
