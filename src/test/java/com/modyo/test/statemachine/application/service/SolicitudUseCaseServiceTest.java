package com.modyo.test.statemachine.application.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.modyo.ms.commons.statemachine.components.StateMachinePersistStateChangeHandler;
import com.modyo.test.statemachine.application.port.out.CreateSolicitudPort;
import com.modyo.test.statemachine.application.port.out.LoadSolicitudPort;
import com.modyo.test.statemachine.application.port.out.SaveSolicitudPort;
import com.modyo.test.statemachine.domain.model.Estado;
import com.modyo.test.statemachine.domain.model.Evento;
import com.modyo.test.statemachine.domain.model.Solicitud;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SolicitudUseCaseServiceTest {

  @Mock
  private CreateSolicitudPort createSolicitudPort;

  @Mock
  private LoadSolicitudPort loadSolicitudPort;
  @Mock
  private SaveSolicitudPort saveSolicitudPort;

  @Mock
  private StateMachinePersistStateChangeHandler stateMachineHandler;

  @InjectMocks
  private SolicitudUseCaseService solicitudUseCaseService;

  @Captor
  ArgumentCaptor<Solicitud> solicitudCaptor;

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
    when(loadSolicitudPort.load(any())).thenReturn(solicitud);
    assertSame(solicitud, solicitudUseCaseService.getOne(1L));
    verify(loadSolicitudPort).load(any());
  }

  @Test
  void testNewSolicitud() {
    Solicitud solicitud = new Solicitud();
    when(createSolicitudPort.create(any())).thenReturn(solicitud);
    assertSame(solicitud, solicitudUseCaseService.newSolicitud("Name"));
    verify(createSolicitudPort).create(any());
  }


  @Test
  void testProcessEvent() {
    Solicitud solicitud = new Solicitud();
    solicitud.setId(123L);
    solicitud.setName("test");
    solicitud.setState(Estado.S1);
    when(loadSolicitudPort.loadAndLock(any())).thenReturn(solicitud);
    when(loadSolicitudPort.load(any())).thenReturn(solicitud);
    solicitudUseCaseService.processEvent(123L, "E2");
    verify(stateMachineHandler).sendEvent(solicitud,"123",solicitud.getState(), Evento.E2);
  }

}

