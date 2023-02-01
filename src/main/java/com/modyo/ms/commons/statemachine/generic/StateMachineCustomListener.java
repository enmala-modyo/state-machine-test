package com.modyo.ms.commons.statemachine.generic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;


@Slf4j
public class StateMachineCustomListener extends StateMachineListenerAdapter<String, String>{



  @Override
  public void stateChanged(State from, State to) {
    log.info("Transitioned from {} to {}", from == null ? "none" : from.getId(), to.getId());
    //extra logic...
  }


}
