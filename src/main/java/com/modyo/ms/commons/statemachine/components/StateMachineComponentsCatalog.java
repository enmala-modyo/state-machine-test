package com.modyo.ms.commons.statemachine.components;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StateMachineComponentsCatalog<S, E> {

  private final Map<String, Action<S, E>> actions;
  private final Map<String, Guard<S, E>> guards;

  public Action<S, E> getAction(String name) {
    return actions.getOrDefault(name, doNothing());
  }

  public Guard<S, E> getGuard(String name) {
    return guards.getOrDefault(name, alwaysFail());
  }

  private Action<S, E> doNothing() {
    return context -> {
      // do nothing
    };
  }

  private Guard<S, E> alwaysFail() {
    return context -> false;
  }
}
