package com.modyo.test.statemachine.config;

import com.modyo.ms.commons.statemachine.components.DefaultStateChangeLoggerListener;
import com.modyo.ms.commons.statemachine.components.StateMachineComponentsCatalog;
import com.modyo.test.statemachine.domain.model.Estado;
import com.modyo.test.statemachine.domain.model.Evento;
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
public class StateMachineConfig extends StateMachineConfigurerAdapter<Estado, Evento> {

  public static final String SM_ENTITY_HEADER = "solicitud";
  private final DefaultStateChangeLoggerListener<Estado, Evento> stateChangeLoggerListener;
  private final StateMachineComponentsCatalog<Estado, Evento> componentsCatalog;

  @Override
  public void configure(StateMachineStateConfigurer<Estado, Evento> states) throws Exception {
    states
        .withStates()
        .initial(Estado.INIT)
        .junction(Estado.S2)
        .end(Estado.END)
        .state(Estado.INIT)
        .state(
            Estado.S1,
            componentsCatalog.getAction("s1EntryAction"),
            componentsCatalog.getAction("s1ExitAction")
        )
        .state(Estado.S2)
        .state(Estado.S3)
        .state(Estado.END);
  }

  @Override
  public void configure(StateMachineTransitionConfigurer<Estado, Evento> transitions) throws Exception {
    transitions
        .withExternal()
        .source(Estado.INIT).target(Estado.S1).event(Evento.E0)
        .and().withExternal()
        .source(Estado.S1).target(Estado.S2).event(Evento.E1)
        .and().withExternal()
        .source(Estado.S1).target(Estado.S3).event(Evento.E2)
        .and()
        .withJunction()
        .source(Estado.S2)
        .first(Estado.END, componentsCatalog.getGuard("s2Guard"))
        .then(Estado.END, componentsCatalog.getGuard("s3Guard"))
        .last(Estado.S3)
        .and().withExternal()
        .source(Estado.S3).target(Estado.END).event(Evento.E4);
  }

  @Override
  public void configure(StateMachineConfigurationConfigurer<Estado, Evento> config) throws Exception {
    config
        .withConfiguration()
        .listener(stateChangeLoggerListener);
  }
}


