package com.modyo.test.statemachine.config;

import com.modyo.ms.commons.statemachine.components.StateMachineComponentsCatalog;
import com.modyo.test.statemachine.domain.enums.Events;
import com.modyo.test.statemachine.domain.enums.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Configuration
@EnableStateMachine(name = "solicitudStateMachine")
@Slf4j
public class StateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

  public static final String SM_ENTITY_HEADER = "solicitud";

  @Autowired
  private StateMachineComponentsCatalog componentsCatalog;

  @Override
  public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
    states
        .withStates()
        .initial(States.SI.name())
        .choice(States.S2.name())
        .end(States.SF.name())
        .state(States.SI.name())
        .state(States.S1.name(), componentsCatalog.getAction("s1EntryAction"),
            componentsCatalog.getAction("s1ExitAction"))
        .state(States.S2.name())
        .state(States.S3.name())
        .state(States.SF.name());
  }

  @Override
  public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
    transitions
        .withExternal()
        .source(States.SI.name()).target(States.S1.name()).event(Events.E0.name())
        .and().withExternal()
        .source(States.S1.name()).target(States.S2.name()).event(Events.E1.name())
        .and().withExternal()
        .source(States.S1.name()).target(States.S3.name()).event(Events.E2.name())
        .and().withChoice()
        .source(States.S2.name()).first(States.SF.name(), componentsCatalog.getGuard("s2Guard")).last(States.S3.name())
        .and().withExternal()
        .source(States.S3.name()).target(States.SF.name()).event(Events.E4.name());
  }

  @Override
  public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
    StateMachineListenerAdapter<String, String> adapter = new StateMachineListenerAdapter<>() {
      @Override
      public void stateChanged(State<String, String> from, State<String, String> to) {
        log.info(String.format("State Changed from : %s, to: %s", from, to));
      }
    };

    config.withConfiguration().listener(adapter);
  }
}


