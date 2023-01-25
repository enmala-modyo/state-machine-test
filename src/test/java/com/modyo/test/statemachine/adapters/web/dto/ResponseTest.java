package com.modyo.test.statemachine.adapters.web.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ResponseTest {

  /**
   * Method under test: {@link Response#Response(Object)}
   */
  @Test
  void testConstructor() {
    assertEquals("Data", (new Response<>("Data")).getData());
  }
}

