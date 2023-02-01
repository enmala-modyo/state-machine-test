package com.modyo.ms.commons.statemachine.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler.PersistStateChangeListener;

@Configuration
public class PersistHandlerConfig {

  @Bean
  public PersistStateMachineHandler persistStateMachineHandler(
      @Autowired StateMachine<String, String> stateMachine,
      @Autowired PersistStateChangeListener persistStateChangeListener) {
    PersistStateMachineHandler handler = new PersistStateMachineHandler(stateMachine);
    handler.addPersistStateChangeListener(persistStateChangeListener);
    return handler;
  }
}
