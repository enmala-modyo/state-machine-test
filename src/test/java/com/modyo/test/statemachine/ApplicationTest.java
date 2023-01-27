package com.modyo.test.statemachine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class ApplicationTest {
  @Test
  void main_CanBuildContext() {
    assertDoesNotThrow(()-> Application.main(new String[]{}));
  }
}
