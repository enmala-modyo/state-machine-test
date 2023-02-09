package com.modyo.test.statemachine.application.service;

import static com.modyo.test.statemachine.config.StateMachineConfig.SM_ENTITY_HEADER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.modyo.test.statemachine.application.port.out.SaveSolicitudPort;
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

@ContextConfiguration(classes = {LocalPersistStateChangeInterceptor.class})
@ExtendWith(SpringExtension.class)
class LocalPersistStateChangeInterceptorTest {

  @MockBean
  SaveSolicitudPort savePort;
  @Autowired
  LocalPersistStateChangeInterceptor interceptor;


  @BeforeEach
  void setUp() {
  }

  @Test
  void preStateChange_NoMessage_DoNothing() {
    assertDoesNotThrow(() -> interceptor.preStateChange(null, null, null, null, null));
  }

  @Test
  void preStateChange_HeaderNotFound_DoNothing() {
    Message<String> message = mock(Message.class);
    when(message.getHeaders()).thenReturn(new MessageHeaders(Map.of()));
    assertDoesNotThrow(() -> interceptor.preStateChange(null, message, null, null, null));
  }

  @Test
  void preStateChange_SolicitudNotFound_DoNothing() {
    Message<String> message = mock(Message.class);
    var headers = new HashMap<String, Object>();
    headers.put(SM_ENTITY_HEADER, null);
    when(message.getHeaders()).thenReturn(new MessageHeaders(headers));
    assertDoesNotThrow(() -> interceptor.preStateChange(null, message, null, null, null));
  }
}
