package com.modyo.test.statemachine.adapters.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatabaseAdapterTest {

  DatabaseAdapter databaseAdapter;

  @BeforeEach
  void setUp() {
    databaseAdapter = new DatabaseAdapter();
  }

  @Test
  void test() {
    assertThat(databaseAdapter).isNotNull();
  }
}
