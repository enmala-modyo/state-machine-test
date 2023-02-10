package com.modyo.ms.commons.statemachine.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public abstract class AbstractGuard<T, S, E> implements Guard<S, E> {
  final Class<T> typeParameterClass;

  protected AbstractGuard() {
    Type superclass = this.getClass().getGenericSuperclass();
    if (!(superclass instanceof ParameterizedType)) {
      throw new IllegalStateException("Superclass is not a ParameterizedType");
    }

    Type[] actualTypeArguments = ((ParameterizedType) superclass).getActualTypeArguments();
    if (actualTypeArguments.length == 0) {
      throw new IllegalStateException("Actual type arguments are empty");
    }
    typeParameterClass = (Class<T>) actualTypeArguments[0];

  }

  private String getEntityHeaderName(){
    return typeParameterClass.getName();
  }

  protected T getEntity(StateContext<S, E> context) {
    return (T) context.getMessageHeader(getEntityHeaderName());
  }
}
