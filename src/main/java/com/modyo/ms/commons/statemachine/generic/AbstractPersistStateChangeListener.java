package com.modyo.ms.commons.statemachine.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler.PersistStateChangeListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
public abstract class AbstractPersistStateChangeListener<T> implements PersistStateChangeListener {

  final Class<T> typeParameterClass;

  protected AbstractPersistStateChangeListener() {
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


  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void onPersist(State<String, String> state, Message<String> message,
      Transition<String, String> transition, StateMachine<String, String> stateMachine) {
    if (message != null && message.getHeaders().containsKey(getEntityHeaderName())) {
      T entity = message.getHeaders().get(getEntityHeaderName(), typeParameterClass);
      if(entity!=null) {
        this.updateState(entity, state.getId());
        saveEntity(entity);
      }
    }
  }

  protected abstract String getEntityHeaderName();
  protected abstract void saveEntity(T entity);
  protected abstract void updateState(T entity, String state);

}
