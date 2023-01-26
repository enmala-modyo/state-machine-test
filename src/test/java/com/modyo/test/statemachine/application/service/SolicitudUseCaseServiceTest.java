package com.modyo.test.statemachine.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.modyo.test.statemachine.application.port.out.CreateSolicitudPort;
import com.modyo.test.statemachine.application.port.out.LoadSolicitudPort;
import com.modyo.test.statemachine.config.statemachine.EventsEnum;
import com.modyo.test.statemachine.config.statemachine.StatesEnum;
import com.modyo.test.statemachine.domain.model.Solicitud;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.statemachine.config.StateMachineFactory;

@SpringBootTest
class SolicitudUseCaseServiceTest {

  @MockBean
  private CreateSolicitudPort createSolicitudPort;

  @MockBean
  private LoadSolicitudPort loadSolicitudPort;

  @MockBean
  private SolicitudStateChangeInterceptor solicitudStateChangeInterceptor;

  @Autowired
  private SolicitudUseCaseService solicitudUseCaseService;

  @Autowired
  private StateMachineFactory<StatesEnum, EventsEnum> stateMachineFactory;

  @BeforeEach
  void setup() {

  }

  @Test
  void testGetAll() {
    ArrayList<Solicitud> solicitudList = new ArrayList<>();
    when(loadSolicitudPort.loadAllActive()).thenReturn(solicitudList);
    List<Solicitud> actualAll = solicitudUseCaseService.getAll();
    assertSame(solicitudList, actualAll);
    assertTrue(actualAll.isEmpty());
    verify(loadSolicitudPort).loadAllActive();
  }

  @Test
  void testGetOne() {
    Solicitud solicitud = new Solicitud();
    when(loadSolicitudPort.load((Long) any())).thenReturn(solicitud);
    assertSame(solicitud, solicitudUseCaseService.getOne(1L));
    verify(loadSolicitudPort).load((Long) any());
  }

  @Test
  void testNewSolicitud() {
    Solicitud solicitud = new Solicitud();
    when(createSolicitudPort.create((String) any())).thenReturn(solicitud);
    assertSame(solicitud, solicitudUseCaseService.newSolicitud("Name"));
    verify(createSolicitudPort).create((String) any());
  }


  @Test
  void testProcessEvent() {
    Solicitud solicitud = new Solicitud();
    solicitud.setId(123L);
    solicitud.setName("test");
    solicitud.setState("S1");
    when(loadSolicitudPort.loadAndLock(any())).thenReturn(solicitud);
    var result = solicitudUseCaseService.processEvent(123L, "E2");
    assertEquals(solicitud, result);
  }

}

