package com.modyo.ms.commons.statemachine.generic;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public abstract class AbstractAction<T> implements Action<String, String> {

  protected T getEntity(StateContext<String, String> context){
    return (T) context.getMessageHeader(getEntityHeaderName());
  }

  protected String getSource(StateContext<String, String> context) {
    return context.getTransition().getSource().getId();
  }

  protected String getTarget(StateContext<String, String> context) {
    return context.getTransition().getTarget().getId();
  }

  protected abstract String getEntityHeaderName();
}
