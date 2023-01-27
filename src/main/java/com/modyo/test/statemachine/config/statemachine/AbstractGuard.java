package com.modyo.test.statemachine.config.statemachine;

import static com.modyo.test.statemachine.config.statemachine.StateMachineConfig.SM_ENTITY_HEADER;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public abstract class AbstractGuard<T> implements Guard<String, String> {

  protected T getEntity(StateContext<String, String> context){
    return (T) context.getMessageHeader(SM_ENTITY_HEADER);
  }

}
