package com.modyo.test.statemachine.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import com.modyo.test.statemachine.application.port.out.SaveSolicitudPort;
import com.modyo.test.statemachine.domain.model.Estado;
import com.modyo.test.statemachine.domain.model.Solicitud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StateChangePersistenceInterceptorTest {

  @Mock
  SaveSolicitudPort savePort;
  @InjectMocks
  StateChangePersistenceInterceptor interceptor;


  @BeforeEach
  void setUp() {
  }

  @Test
  void saveEntityTest(){
    var solicitud = new Solicitud(1L, Estado.S1,"foo");
    assertDoesNotThrow(()->interceptor.saveEntity(solicitud));
    verify(savePort).save(solicitud);
  }

  @Test
  void updateStateTest(){
    var solicitud = new Solicitud(1L, Estado.S1,"foo");
    interceptor.updateState(solicitud,Estado.S3);
    assertEquals(Estado.S3,solicitud.getState());
  }


}
