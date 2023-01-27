package com.modyo.test.statemachine.config.statemachine;

import static com.modyo.test.statemachine.config.statemachine.StateMachineConfig.SM_ENTITY_HEADER;

import java.lang.reflect.ParameterizedType;
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
    typeParameterClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
        .getActualTypeArguments()[0];
  }


  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void onPersist(State<String, String> state, Message<String> message,
      Transition<String, String> transition, StateMachine<String, String> stateMachine) {
    if (message != null && message.getHeaders().containsKey(SM_ENTITY_HEADER)) {
      T entity = message.getHeaders().get(SM_ENTITY_HEADER, typeParameterClass);
      if(entity!=null) {
        this.setState(entity, state.getId());
        saveEntity(entity);
      }
    }
  }

  protected abstract void saveEntity(T entity);
  protected abstract void setState(T entity, String state);

}
