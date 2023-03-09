package com.modyo.test.statemachine.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.modyo.ms.commons.core.exceptions.BusinessErrorException;
import org.junit.jupiter.api.Test;

class EventoTest {

  @Test
  void ofWhenNotFoundThrowsException() {
    assertThrows(BusinessErrorException.class, ()->Evento.of("ERROR"));
  }

  @Test
  void ofWhenFoundReturnsEvento() {
    assertEquals(Evento.E0,Evento.of("E0"));
  }

  @Test
  void ofWhenNullThrowsException() {
    assertThrows(BusinessErrorException.class, ()->Evento.of(null));
  }
}
