package com.modyo.test.statemachine.config;

import com.modyo.ms.commons.statemachine.components.StateMachineComponentsCatalog;
import com.modyo.test.statemachine.domain.statemachine.EventsEnum;
import com.modyo.test.statemachine.domain.statemachine.StatesEnum;
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
        .initial(StatesEnum.SI.name())
        .choice(StatesEnum.S2.name())
        .end(StatesEnum.SF.name())
        .state(StatesEnum.SI.name())
        .state(StatesEnum.S1.name(), componentsCatalog.getAction("s1EntryAction"),
            componentsCatalog.getAction("s1ExitAction"))
        .state(StatesEnum.S2.name())
        .state(StatesEnum.S3.name())
        .state(StatesEnum.SF.name());
  }

  @Override
  public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
    transitions
        .withExternal()
        .source(StatesEnum.SI.name()).target(StatesEnum.S1.name()).event(EventsEnum.E0.name())
        .and().withExternal()
        .source(StatesEnum.S1.name()).target(StatesEnum.S2.name()).event(EventsEnum.E1.name())
        .and().withExternal()
        .source(StatesEnum.S1.name()).target(StatesEnum.S3.name()).event(EventsEnum.E2.name())
        .and().withChoice()
        .source(StatesEnum.S2.name()).first(StatesEnum.SF.name(), componentsCatalog.getGuard("s2Guard")).last(StatesEnum.S3.name())
        .and().withExternal()
        .source(StatesEnum.S3.name()).target(StatesEnum.SF.name()).event(EventsEnum.E4.name());
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


