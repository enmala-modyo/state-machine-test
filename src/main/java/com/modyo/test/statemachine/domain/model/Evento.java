package com.modyo.test.statemachine.domain.model;

import com.modyo.ms.commons.core.exceptions.BusinessErrorException;
import java.util.Arrays;
import java.util.function.Supplier;

public enum Evento {
  E0, E1, E2, E3, E4;

  public static Evento of(String name) {
    return Arrays.stream(Evento.values())
        .filter(evento -> evento.name().equalsIgnoreCase(name))
        .findFirst()
        .orElseThrow(handleEventNotFound(name));
  }

  private static Supplier<BusinessErrorException> handleEventNotFound(String name) {
    String message = String.format("Evento '%s' no es válido", name);
    return () -> new BusinessErrorException(message);
  }
}
