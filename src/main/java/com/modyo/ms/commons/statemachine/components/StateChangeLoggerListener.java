package com.modyo.ms.commons.statemachine.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StateChangeLoggerListener<S, E> extends StateMachineListenerAdapter<S, E> {

  @Override
  public void stateChanged(State from, State to) {
    log.info("Transitioned from {} to {}", from == null ? "none" : from.getId(), to.getId());
    //extra logic...
  }
}
