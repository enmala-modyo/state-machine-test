package com.modyo.test.statemachine.adapters.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.modyo.ms.commons.core.exceptions.NotFoundException;
import com.modyo.test.statemachine.adapters.web.dto.SolicitudDto;
import com.modyo.test.statemachine.adapters.web.dto.SolicitudDtoMapper;
import com.modyo.test.statemachine.application.port.in.SolicitudUseCase;
import com.modyo.test.statemachine.domain.model.Solicitud;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StateMachineControllerTest {

  @Mock
  private SolicitudDtoMapper solicitudDtoMapper;

  @Mock
  private SolicitudUseCase solicitudUseCase;

  @InjectMocks
  private StateMachineController stateMachineController;

  private final SolicitudDto solicitudDto = new SolicitudDto(1L, "foo", "INIT");

  @BeforeEach
  void setUp() {
    lenient().when(solicitudDtoMapper.toDto(any())).thenReturn(solicitudDto);
  }

  @Test
  void testCreateSolicitud() {

    when(solicitudUseCase.newSolicitud(any())).thenReturn(new Solicitud());

    var responseEntity = stateMachineController.createSolicitud("foo");

    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals(solicitudDto, responseEntity.getBody().getData());
    verify(solicitudUseCase).newSolicitud("foo");
  }

  @Test
  void testGetOneSolicitud() {
    when(solicitudUseCase.getOne(any())).thenReturn(new Solicitud());

    var responseEntity = stateMachineController.getSolicitud(1L);

    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals(solicitudDto, responseEntity.getBody().getData());
    verify(solicitudUseCase).getOne(1L);
  }

  @Test
  void testGetOneSolicitudNotFound() {
    when(solicitudUseCase.getOne(any())).thenThrow(new NotFoundException());

    assertThrows(NotFoundException.class, () -> stateMachineController.getSolicitud(1L));
  }

  @Test
  void testGetAllSolicitud() {
    when(solicitudUseCase.getAll()).thenReturn(List.of(new Solicitud(), new Solicitud()));

    var responseEntity = stateMachineController.getSolicitudes();

    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals(2, responseEntity.getBody().getData().size());
    assertEquals(solicitudDto, responseEntity.getBody().getData().get(0));
    verify(solicitudUseCase).getAll();
    verify(solicitudDtoMapper, times(2)).toDto(any());
  }

  @Test
  void testUpdateSolicitud() {
    when(solicitudUseCase.processEvent(any(), any())).thenReturn(new Solicitud());

    var responseEntity = stateMachineController.updateSolicitud(1L, "E1");

    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals(solicitudDto, responseEntity.getBody().getData());
    verify(solicitudUseCase).processEvent(1L, "E1");
  }
}

