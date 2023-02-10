package com.modyo.ms.commons.statemachine.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public abstract class AbstractStateMachinePersistInterceptor<T, S, E> extends StateMachineInterceptorAdapter<S, E> {

  final Class<T> typeParameterClass;

  protected abstract void saveEntity(T entity);

  protected abstract void updateState(T entity, S state);


  protected AbstractStateMachinePersistInterceptor() {
    log.debug("Instantiating AbstractStateMachineListenerAdapter");
    Type superclass = this.getClass().getGenericSuperclass();
    if (!(superclass instanceof ParameterizedType)) {
      log.error("Superclass is not a ParameterizedType");
      throw new IllegalStateException("Superclass is not a ParameterizedType");
    }

    Type[] actualTypeArguments = ((ParameterizedType) superclass).getActualTypeArguments();
    if (actualTypeArguments.length == 0) {
      log.error("Actual type arguments are empty");
      throw new IllegalStateException("Actual type arguments are empty");
    }
    typeParameterClass = (Class<T>) actualTypeArguments[0];

  }

  public String getEntityHeaderName() {
    return typeParameterClass.getName();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void preStateChange(
      State<S, E> state,
      Message<E> message,
      Transition<S, E> transition,
      StateMachine<S, E> stateMachine,
      StateMachine<S, E> rootStateMachine) {
    log.info("onPersist");
    if (message != null && message.getHeaders().containsKey(getEntityHeaderName())) {
      T entity = message.getHeaders().get(getEntityHeaderName(), typeParameterClass);
      if (entity != null) {
        this.updateState(entity, state.getId());
        saveEntity(entity);
      }
    }
  }
}
