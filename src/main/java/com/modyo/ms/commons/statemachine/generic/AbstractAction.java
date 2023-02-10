package com.modyo.ms.commons.statemachine.generic;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public abstract class AbstractAction<T, S, E> implements Action<S, E> {

  protected abstract String getEntityHeaderName();

  protected T getEntity(StateContext<S, E> context) {
    return (T) context.getMessageHeader(getEntityHeaderName());
  }

  protected S getSource(StateContext<S, E> context) {
    return context.getTransition().getSource().getId();
  }

  protected S getTarget(StateContext<S, E> context) {
    return context.getTransition().getTarget().getId();
  }
}
