package com.modyo.ms.commons.statemachine.generic;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractPersistStateChangeListenerTest {

  static class ConcretePersistStateChangeListener extends AbstractPersistStateChangeListener{

    @Override
    protected String getEntityHeaderName() {
      return "header";
    }

    @Override
    protected void saveEntity(Object entity) {
    }

    @Override
    protected void updateState(Object entity, String state) {
    }
  }

  @BeforeEach
  void setup(){

  }

  @Test
  void testContructor_NoParameterizedType_ThrowsException(){
    assertThrows(IllegalStateException.class, ConcretePersistStateChangeListener::new);
  }

}

