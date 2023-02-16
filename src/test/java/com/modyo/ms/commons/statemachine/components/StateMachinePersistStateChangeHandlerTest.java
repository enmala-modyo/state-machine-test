package com.modyo.ms.commons.statemachine.components;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.modyo.ms.commons.statemachine.generic.AbstractStateMachinePersistInterceptor;
import com.modyo.test.statemachine.domain.model.Estado;
import com.modyo.test.statemachine.domain.model.Evento;
import com.modyo.test.statemachine.domain.model.Solicitud;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.access.StateMachineAccessor;
import org.springframework.statemachine.config.StateMachineFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class StateMachinePersistStateChangeHandlerTest {

  @Mock
  private StateMachineFactory<Estado, Evento> stateMachineFactory;
  @Mock
  private AbstractStateMachinePersistInterceptor<Solicitud, Estado, Evento> persistInterceptor;
  @Mock
  private StateMachine<Estado,Evento> stateMachine;

  @InjectMocks
  StateMachinePersistStateChangeHandler<Solicitud, Estado, Evento> handler;

  @BeforeEach
  void setUp() {
    var accessor = mock(StateMachineAccessor.class);
    doAnswer(answer -> {
      Consumer<StateMachineAccess<Estado,Evento>> consumer = answer.getArgument(0, Consumer.class);
      var access = mock(StateMachineAccess.class);
      when(access.resetStateMachineReactively(any())).thenReturn(Mono.empty());
      consumer.accept(access);
      return null;
    }).when(accessor).doWithAllRegions(any());
    when(stateMachineFactory.getStateMachine(anyString())).thenReturn(stateMachine);
    when(stateMachine.stopReactively()).thenReturn(Mono.empty());
    when(stateMachine.startReactively()).thenReturn(Mono.empty());
    when(stateMachine.sendEvent((Mono<Message<Evento>>) any())).thenReturn(Flux.empty());
    when(stateMachine.getStateMachineAccessor()).thenReturn(accessor);
  }

  @Test
  void sendEvent() {
    handler.sendEvent(new Solicitud(), "1",Estado.S1, Evento.E0);
    verify(stateMachine).sendEvent((Mono<Message<Evento>>) any());
  }
}
