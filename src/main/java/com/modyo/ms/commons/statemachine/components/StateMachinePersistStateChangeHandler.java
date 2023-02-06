package com.modyo.ms.commons.statemachine.components;

import com.modyo.ms.commons.statemachine.generic.AbstractStateMachinePersistInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
@ConditionalOnBean({AbstractStateMachinePersistInterceptor.class})
public class StateMachinePersistStateChangeHandler {

  private final StateMachineFactory<String, String> stateMachineFactory;
  private final AbstractStateMachinePersistInterceptor persistInterceptor;

  public void sendEvent(Object entity, String entityId, String state, String event) {
    Message msg = MessageBuilder.withPayload(event)
        .setHeader(persistInterceptor.getEntityHeaderName(), entity)
        .build();

    build(entityId, state).sendEvent(Mono.just(msg)).subscribe();
  }

  private StateMachine<String, String> build(String entityId, String entityState) {
    StateMachine<String, String> sm = stateMachineFactory.getStateMachine(entityId);
    sm.stopReactively().block();
    sm.getStateMachineAccessor()
        .doWithAllRegions(sma -> {
          sma.addStateMachineInterceptor(persistInterceptor);
          sma.resetStateMachineReactively(
              new DefaultStateMachineContext<>(entityState, null, null, null)).subscribe();
        });
    sm.startReactively().block();
    return sm;
  }
}
