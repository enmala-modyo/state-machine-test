package com.modyo.test.statemachine.adapters.web;

import static org.hamcrest.Matchers.is;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modyo.test.statemachine.adapters.web.dto.Response;
import com.modyo.test.statemachine.adapters.web.dto.SolicitudDto;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StateMachineControllerIntegrationTest {

  public static final String URL_SOLICITUDES_ID_EVENT = "/statemachine/solicitudes/{id}/{event}";
  private static ClientAndServer mockServer;
  @Autowired
  private MockMvc mockMvc;

  SolicitudDto expectedDto;
  ObjectMapper objectMapper = new ObjectMapper();

  @BeforeAll
  public static void startMockServer() {
    mockServer = startClientAndServer(1080);
  }

  @AfterAll
  public static void stopMockServer() {
    mockServer.stop();
  }

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
            patch(URL_SOLICITUDES_ID_EVENT, 1, "E2")
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
            patch(URL_SOLICITUDES_ID_EVENT, 1, "E0")
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
  }

  @Test
  @Order(6)
  void givenOneSolicitudInStateS1_whenSendE1EventAndExternalAsnwerNo_thenVerifyNewStateIsS3() throws Exception {
    createExpectationForYesNoApiNo();
    var expectedState = "S3";
    mockMvc.perform(
            patch(URL_SOLICITUDES_ID_EVENT, 1, "E1")
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.state", is(expectedState)));
  }

  @Test
  @Order(7)
  @Sql(statements = {"insert into solicitud values(null,'test','S1')"})
  void givenOneSolicitudInStateS1_whenSendE1EventAndExternalAsnwerYes_thenVerifyNewStateIsEnd() throws Exception {
    createExpectationForYesNoApiYes();
    var expectedState = "END";
    mockMvc.perform(
            patch(URL_SOLICITUDES_ID_EVENT, 2, "E1")
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.state", is(expectedState)));
  }

  private void createExpectationForYesNoApiNo() throws IOException {
    InputStream is = this.getClass().getResourceAsStream("/responses/no.json");
    String responseBody = new String(is.readAllBytes());
    new MockServerClient("127.0.0.1", 1080)
        .when(
            request()
                .withMethod("GET")
                .withPath("/yesno/api/"), Times.exactly(1)
        ).respond(
            response()
                .withStatusCode(200)
                .withContentType(MediaType.APPLICATION_JSON)
                .withBody(responseBody)
        );
  }

  private void createExpectationForYesNoApiYes() throws IOException {
    InputStream is = this.getClass().getResourceAsStream("/responses/yes.json");
    String responseBody = new String(is.readAllBytes());
    new MockServerClient("127.0.0.1", 1080)
        .when(
            request()
                .withMethod("GET")
                .withPath("/yesno/api/"), Times.exactly(1)
        ).respond(
            response()
                .withStatusCode(200)
                .withContentType(MediaType.APPLICATION_JSON)
                .withBody(responseBody)
        );
  }
}
