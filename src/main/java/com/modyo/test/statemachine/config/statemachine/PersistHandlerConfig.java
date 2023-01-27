package com.modyo.test.statemachine.config.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;

@Configuration
public class PersistHandlerConfig {

  @Bean
  public PersistStateMachineHandler persistStateMachineHandler(@Autowired StateMachine<String, String> solicitudStateMachine,
      @Autowired LocalPersistStateChangeListener persistStateChangeListener) {
    PersistStateMachineHandler handler = new PersistStateMachineHandler(solicitudStateMachine);
    handler.addPersistStateChangeListener(persistStateChangeListener);
    return handler;
  }
}
