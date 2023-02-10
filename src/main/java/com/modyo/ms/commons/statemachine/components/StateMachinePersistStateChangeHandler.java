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
public class StateMachinePersistStateChangeHandler<T, S, E> {

  private final StateMachineFactory<S, E> stateMachineFactory;
  private final AbstractStateMachinePersistInterceptor<T, S, E> persistInterceptor;

  public void sendEvent(T entity, String entityId, S state, E event) {
    Message<E> message = MessageBuilder
        .withPayload(event)
        .setHeader(persistInterceptor.getEntityHeaderName(), entity)
        .build();

    build(entityId, state)
        .sendEvent(Mono.just(message))
        .subscribe();
  }

  private StateMachine<S, E> build(String entityId, S entityState) {
    StateMachine<S, E> stateMachine = stateMachineFactory.getStateMachine(entityId);
    stateMachine.stopReactively().block();
    stateMachine.getStateMachineAccessor()
        .doWithAllRegions(machineAccess -> {
          machineAccess.addStateMachineInterceptor(persistInterceptor);
          machineAccess.resetStateMachineReactively(
              new DefaultStateMachineContext<>(entityState, null, null, null)).subscribe();
        });
    stateMachine.startReactively().block();
    return stateMachine;
  }
}
