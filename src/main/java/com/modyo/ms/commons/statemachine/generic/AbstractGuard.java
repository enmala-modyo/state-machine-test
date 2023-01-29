package com.modyo.ms.commons.statemachine.generic;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public abstract class AbstractGuard<T> implements Guard<String, String> {

  protected T getEntity(StateContext<String, String> context){
    return (T) context.getMessageHeader(getEntityHeaderName());
  }

  protected abstract String getEntityHeaderName();

}
