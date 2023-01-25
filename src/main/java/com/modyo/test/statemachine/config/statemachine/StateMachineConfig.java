package com.modyo.test.statemachine.config.statemachine;

import com.modyo.test.statemachine.config.statemachine.actions.S1EntryAction;
import com.modyo.test.statemachine.config.statemachine.actions.S1ExitAction;
import com.modyo.test.statemachine.config.statemachine.guards.S2Guard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Configuration
@EnableStateMachineFactory
@Slf4j
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<StatesEnum, EventsEnum> {

  public static final String SM_ENTITY_HEADER = "solicitud";
  @Autowired
  private S2Guard s2Guard;
  @Autowired
  private S1EntryAction s1EntryAction;
  @Autowired
  private S1ExitAction s1ExitAction;

  @Override
  public void configure(StateMachineStateConfigurer<StatesEnum, EventsEnum> states) throws Exception {
    states
        .withStates()
        .initial(StatesEnum.SI)
        .choice(StatesEnum.S2)
        .end(StatesEnum.SF)
        .state(StatesEnum.SI)
        .state(StatesEnum.S1, s1EntryAction, s1ExitAction)
        .state(StatesEnum.S2)
        .state(StatesEnum.S3)
        .state(StatesEnum.SF);
  }

  @Override
  public void configure(StateMachineTransitionConfigurer<StatesEnum, EventsEnum> transitions) throws Exception {
    transitions
        .withExternal()
        .source(StatesEnum.SI).target(StatesEnum.S1).event(EventsEnum.E0)
        .and().withExternal()
        .source(StatesEnum.S1).target(StatesEnum.S2).event(EventsEnum.E1)
        .and().withExternal()
        .source(StatesEnum.S1).target(StatesEnum.S3).event(EventsEnum.E2)
        .and().withChoice()
        .source(StatesEnum.S2).first(StatesEnum.SF, s2Guard).last(StatesEnum.S3)
        .and().withExternal()
        .source(StatesEnum.S3).target(StatesEnum.SF).event(EventsEnum.E4);
  }

  @Override
  public void configure(StateMachineConfigurationConfigurer<StatesEnum, EventsEnum> config) throws Exception {
    StateMachineListenerAdapter<StatesEnum, EventsEnum> adapter = new StateMachineListenerAdapter<>() {
      @Override
      public void stateChanged(State<StatesEnum, EventsEnum> from, State<StatesEnum, EventsEnum> to) {
        log.info(String.format("State Changed from : %s, to: %s", from, to));
      }
    };

    config.withConfiguration().listener(adapter);
  }
}


