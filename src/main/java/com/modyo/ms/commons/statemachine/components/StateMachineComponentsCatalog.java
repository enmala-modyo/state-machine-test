package com.modyo.ms.commons.statemachine.components;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.recipes.support.RunnableAction;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StateMachineComponentsCatalog {

  private final Map<String,Action<String, String>> actions;
  private final Map<String, Guard<String,String>> guards;
  public Action<String, String> getAction(String name){
    var requiredAction = Optional.ofNullable(actions.get(name));
    return requiredAction.orElse(doNothing());
  }

  public Guard<String, String> getGuard(String name){
    var requiredGuard = Optional.ofNullable(guards.get(name));
    return requiredGuard.orElse(alwaysFailsGuard());
  }

  private RunnableAction doNothing() {
    return new RunnableAction(()->{});
  }

  private Guard<String,String> alwaysFailsGuard() {
    return context -> false;
  }
}
