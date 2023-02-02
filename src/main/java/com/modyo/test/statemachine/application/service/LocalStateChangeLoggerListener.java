package com.modyo.test.statemachine.application.service;

import com.modyo.ms.commons.statemachine.components.StateChangeLoggerListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.Message;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

@Component
@Primary
@Slf4j
public class LocalStateChangeLoggerListener extends StateChangeLoggerListener {

  @Override
  public void eventNotAccepted(Message<String> event) {
    super.eventNotAccepted(event);
    log.info("Event Rejected: {} ", event);
  }

  @Override
  public void stateChanged(State from, State to) {
    super.stateChanged(from, to);
    log.info("Event Accepted");
  }


}
