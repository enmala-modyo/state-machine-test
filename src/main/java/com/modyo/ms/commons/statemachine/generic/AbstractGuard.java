package com.modyo.ms.commons.statemachine.generic;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public abstract class AbstractGuard<T, S, E> implements Guard<S, E> {

  protected abstract String getEntityHeaderName();

  protected T getEntity(StateContext<S, E> context) {
    return (T) context.getMessageHeader(getEntityHeaderName());
  }
}
