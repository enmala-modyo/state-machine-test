package com.modyo.test.statemachine.config.statemachine;

import static com.modyo.test.statemachine.config.statemachine.StateMachineConfig.SM_ENTITY_HEADER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.modyo.test.statemachine.application.port.out.SaveSolicitudPort;
import com.modyo.test.statemachine.application.service.LocalPersistStateChangeListener;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {LocalPersistStateChangeListener.class})
@ExtendWith(SpringExtension.class)
class AbstractPersistStateChangeListenerTest {

  @MockBean
  SaveSolicitudPort savePort;
  @Autowired
  LocalPersistStateChangeListener listener;

  @BeforeEach
  void setUp() {
  }

  @Test
  void onPersist_NoMessage_DoNothing() {
    assertDoesNotThrow(() -> listener.onPersist(null, null, null, null));
  }

  @Test
  void onPersist_HeaderNotFound_DoNothing() {
    Message<String> message = mock(Message.class);
    when(message.getHeaders()).thenReturn(new MessageHeaders(Map.of()));
    assertDoesNotThrow(() -> listener.onPersist(null, message, null, null));
  }

  @Test
  void onPersist_SolicitudNotFound_DoNothing() {
    Message<String> message = mock(Message.class);
    var headers = new HashMap<String,Object>();
    headers.put(SM_ENTITY_HEADER,null);
    when(message.getHeaders()).thenReturn(new MessageHeaders(headers));
    assertDoesNotThrow(() -> listener.onPersist(null, message, null, null));
  }
}
