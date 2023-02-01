package com.modyo.ms.commons.statemachine.utils;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.recipes.persist.AbstractPersistStateMachineHandler;

public class StateMachineUtils {

  private StateMachineUtils() {
  }

  public static void sendEvent(AbstractPersistStateMachineHandler<String, String> handler, String header, Object entity, String state,
      String event) {
    handler.handleEventWithStateReactively(MessageBuilder.withPayload(event)
            .setHeader(header, entity)
            .build(), state)
        .subscribe();
  }
}
