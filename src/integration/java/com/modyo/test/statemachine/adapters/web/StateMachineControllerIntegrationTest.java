package com.modyo.test.statemachine.adapters.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modyo.test.statemachine.adapters.web.dto.Response;
import com.modyo.test.statemachine.adapters.web.dto.SolicitudDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StateMachineControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  SolicitudDto expectedDto;
  ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setUp() {
    expectedDto = new SolicitudDto(1L, "test", "INIT");
  }

  @Test
  @Order(1)
  void givenPostSolicitud_whenSendReq_thenVerifyCreated() throws Exception {
    var expected = new Response<>(expectedDto);
    mockMvc.perform(
            post("/statemachine/solicitudes")
                .param("name", "test")
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
  }

  @Test
  @Order(2)
  void givenOneSolicitud_whenReq_thenVerifyRetrived() throws Exception {
    var expected = new Response<>(expectedDto);
    mockMvc.perform(
            get("/statemachine/solicitudes/{id}", 1)
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
  }

  @Test
  @Order(3)
  void givenOneSolicitud_whenReqAll_thenVerifyRetrived() throws Exception {
    var expected = new Response<>(List.of(expectedDto));
    mockMvc.perform(
            get("/statemachine/solicitudes")
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
  }

  @Test
  @Order(4)
  void givenOneSolicitud_whenSendInvalidEvent_thenVerifyNothingHappened() throws Exception {
    var expected = new Response<>(expectedDto);
    mockMvc.perform(
            patch("/statemachine/solicitudes/{id}/{event}", 1, "E2")
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
  }

  @Test
  @Order(5)
  void givenOneSolicitud_whenSendValidEvent_thenVerifyNewState() throws Exception {
    expectedDto.setState("S1");
    var expected = new Response<>(expectedDto);
    mockMvc.perform(
            patch("/statemachine/solicitudes/{id}/{event}", 1, "E0")
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
  }
}
