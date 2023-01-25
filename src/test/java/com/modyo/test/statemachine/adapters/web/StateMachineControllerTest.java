package com.modyo.test.statemachine.adapters.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.modyo.test.statemachine.adapters.web.dto.SolicitudDto;
import com.modyo.test.statemachine.adapters.web.dto.SolicitudDtoMapper;
import com.modyo.test.statemachine.application.port.in.SolicitudUseCase;
import com.modyo.test.statemachine.domain.model.Solicitud;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {StateMachineController.class})
@ExtendWith(SpringExtension.class)
class StateMachineControllerTest {

  @MockBean
  private SolicitudDtoMapper solicitudDtoMapper;

  @MockBean
  private SolicitudUseCase solicitudUseCase;

  @Autowired
  private StateMachineController stateMachineController;

  @Test
  void testCreateSolicitud() throws Exception {
    when(solicitudUseCase.newSolicitud((String) any())).thenReturn(new Solicitud());
    when(solicitudDtoMapper.toDto((Solicitud) any())).thenReturn(new SolicitudDto());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
            "/statemachine/solicitudes")
        .param("name", "foo");
    MockMvcBuilders.standaloneSetup(stateMachineController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string("{\"data\":{\"id\":null,\"name\":null,\"state\":null}}"));
  }

  @Test
  void testGetSolicitud() throws Exception {
    when(solicitudUseCase.getOne((Long) any())).thenReturn(new Solicitud());
    when(solicitudDtoMapper.toDto((Solicitud) any())).thenReturn(new SolicitudDto());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/statemachine/solicitudes/{idSolicitud}",
        1L);
    MockMvcBuilders.standaloneSetup(stateMachineController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("{\"data\":{\"id\":null,\"name\":null,\"state\":null}}"));
  }

  @Test
  void testGetSolicitud2() throws Exception {
    when(solicitudUseCase.getAll()).thenReturn(new ArrayList<>());
    when(solicitudUseCase.getOne((Long) any())).thenReturn(new Solicitud());
    when(solicitudDtoMapper.toDto((Solicitud) any())).thenReturn(new SolicitudDto());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/statemachine/solicitudes/{idSolicitud}",
        "", "Uri Variables");
    MockMvcBuilders.standaloneSetup(stateMachineController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("{\"data\":[]}"));
  }

  @Test
  void testGetSolicitudes() throws Exception {
    when(solicitudUseCase.getAll()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/statemachine/solicitudes");
    MockMvcBuilders.standaloneSetup(stateMachineController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("{\"data\":[]}"));
  }

  @Test
  void testGetSolicitudes2() throws Exception {
    when(solicitudUseCase.getAll()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/statemachine/solicitudes");
    getResult.characterEncoding("Encoding");
    MockMvcBuilders.standaloneSetup(stateMachineController)
        .build()
        .perform(getResult)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("{\"data\":[]}"));
  }

  @Test
  void testUpdateSolicitud() throws Exception {
    when(solicitudUseCase.processEvent((Long) any(), (String) any())).thenReturn(new Solicitud());
    when(solicitudDtoMapper.toDto((Solicitud) any())).thenReturn(new SolicitudDto());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
        .patch("/statemachine/solicitudes/{idSolicitud}/{event}", 1L, "Event");
    MockMvcBuilders.standaloneSetup(stateMachineController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("{\"data\":{\"id\":null,\"name\":null,\"state\":null}}"));
  }
}

