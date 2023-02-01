package com.modyo.ms.commons.statemachine.config;

import com.modyo.ms.commons.statemachine.generic.StateMachineCustomListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerAdapterConfig {

  @Bean
  public StateMachineCustomListener stateMachineLogListener() {
    return new StateMachineCustomListener();
  }
}
