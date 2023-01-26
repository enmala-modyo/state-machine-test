package com.modyo.test.statemachine.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.modyo.test.statemachine.application.port.out.LoadSolicitudPort;
import com.modyo.test.statemachine.application.port.out.SaveSolicitudPort;
import com.modyo.test.statemachine.config.statemachine.EventsEnum;
import com.modyo.test.statemachine.config.statemachine.StatesEnum;
import com.modyo.test.statemachine.domain.model.Solicitud;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.statemachine.ObjectStateMachine;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.state.EnumState;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultExtendedState;
import org.springframework.statemachine.transition.AbstractInternalTransition;
import org.springframework.statemachine.transition.Transition;
import org.springframework.statemachine.trigger.EventTrigger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {SolicitudStateChangeInterceptor.class})
@ExtendWith(SpringExtension.class)
class SolicitudStateChangeInterceptorTest {

  @MockBean
  private LoadSolicitudPort loadSolicitudPort;

  @MockBean
  private SaveSolicitudPort saveSolicitudPort;

  @Autowired
  private SolicitudStateChangeInterceptor solicitudStateChangeInterceptor;

  @Test
  void testPreStateChange1() {
    when(loadSolicitudPort.loadAndLock((Long) any())).thenReturn(new Solicitud());
    doNothing().when(saveSolicitudPort).save((Solicitud) any());
    EnumState<StatesEnum, EventsEnum> state = new EnumState<>(StatesEnum.SI);
    GenericMessage<EventsEnum> message = new GenericMessage<>(EventsEnum.E0, new HashMap<>());

    EnumState<StatesEnum, EventsEnum> source = new EnumState<>(StatesEnum.SI);
    ArrayList<Function<StateContext<StatesEnum, EventsEnum>, Mono<Void>>> actions = new ArrayList<>();
    Function<StateContext<StatesEnum, EventsEnum>, Mono<Boolean>> guard = (Function<StateContext<StatesEnum, EventsEnum>, Mono<Boolean>>) mock(
        Function.class);
    AbstractInternalTransition<StatesEnum, EventsEnum> transition = new AbstractInternalTransition<>(
        source, actions,
        EventsEnum.E0, guard, new EventTrigger<>(EventsEnum.E0));

    ArrayList<State<StatesEnum, EventsEnum>> states = new ArrayList<>();
    ArrayList<Transition<StatesEnum, EventsEnum>> transitions = new ArrayList<>();
    ObjectStateMachine<StatesEnum, EventsEnum> stateMachine = new ObjectStateMachine<>(states,
        transitions,
        new EnumState<>(StatesEnum.SI));

    ArrayList<State<StatesEnum, EventsEnum>> states1 = new ArrayList<>();
    ArrayList<Transition<StatesEnum, EventsEnum>> transitions1 = new ArrayList<>();
    solicitudStateChangeInterceptor.preStateChange(state, message, transition, stateMachine,
        new ObjectStateMachine<>(states1, transitions1, new EnumState<>(StatesEnum.SI)));
    verify(loadSolicitudPort).loadAndLock((Long) any());
    verify(saveSolicitudPort).save((Solicitud) any());
  }

  @Test
  void testPreStateChange2() {
    when(loadSolicitudPort.loadAndLock((Long) any())).thenReturn(new Solicitud());
    doNothing().when(saveSolicitudPort).save((Solicitud) any());
    State<StatesEnum, EventsEnum> state = (State<StatesEnum, EventsEnum>) mock(State.class);
    when(state.getId()).thenReturn(StatesEnum.SI);
    EnumState<StatesEnum, EventsEnum> source = new EnumState<>(StatesEnum.SI);
    ArrayList<Function<StateContext<StatesEnum, EventsEnum>, Mono<Void>>> actions = new ArrayList<>();
    Function<StateContext<StatesEnum, EventsEnum>, Mono<Boolean>> guard = (Function<StateContext<StatesEnum, EventsEnum>, Mono<Boolean>>) mock(
        Function.class);
    AbstractInternalTransition<StatesEnum, EventsEnum> transition = new AbstractInternalTransition<>(source, actions,
        EventsEnum.E0, guard, new EventTrigger<>(EventsEnum.E0));

    ArrayList<State<StatesEnum, EventsEnum>> states = new ArrayList<>();
    ArrayList<Transition<StatesEnum, EventsEnum>> transitions = new ArrayList<>();
    ObjectStateMachine<StatesEnum, EventsEnum> objectStateMachine = new ObjectStateMachine<>(states, transitions,
        new EnumState<>(StatesEnum.SI));

    ArrayList<State<StatesEnum, EventsEnum>> states1 = new ArrayList<>();
    ArrayList<Transition<StatesEnum, EventsEnum>> transitions1 = new ArrayList<>();
    solicitudStateChangeInterceptor.preStateChange(state, null, transition, objectStateMachine,
        new ObjectStateMachine<>(states1, transitions1, new EnumState<>(StatesEnum.SI)));
    assertTrue(objectStateMachine.getExtendedState() instanceof DefaultExtendedState);
    assertTrue(objectStateMachine.isComplete());
    assertFalse(objectStateMachine.isAutoStartup());
    assertFalse(objectStateMachine.hasStateMachineError());
    assertEquals(0, objectStateMachine.getPhase());
  }

}

