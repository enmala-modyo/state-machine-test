package com.modyo.test.statemachine.config;

import com.modyo.ms.commons.statemachine.components.StateChangeLoggerListener;
import com.modyo.ms.commons.statemachine.components.StateMachineComponentsCatalog;
import com.modyo.test.statemachine.domain.enums.Events;
import com.modyo.test.statemachine.domain.enums.States;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory(name = "solicitudStateMachineFactory")
@Slf4j
@RequiredArgsConstructor
public class StateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

  public static final String SM_ENTITY_HEADER = "solicitud";
  private final StateChangeLoggerListener stateChangeLoggerListener;
  private final StateMachineComponentsCatalog componentsCatalog;

  @Override
  public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
    states
            .withStates()
            .initial(States.SI.name())
            .junction(States.S2.name())
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
            .and()
            .withJunction()
            .source(States.S2.name())
            .first(States.SF.name(), componentsCatalog.getGuard("s2Guard"))
            .then(States.SF.name(), componentsCatalog.getGuard("s3Guard"))
            .last(States.S3.name())
            .and().withExternal()
            .source(States.S3.name()).target(States.SF.name()).event(Events.E4.name());
  }

  @Override
  public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
    config.withConfiguration().listener(stateChangeLoggerListener);
  }
}


