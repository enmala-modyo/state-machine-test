package com.modyo.test.statemachine.adapters.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.modyo.test.statemachine.adapters.web.dto.GreetingDto;
import com.modyo.test.statemachine.application.port.in.InputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GetGreetingControllerTest {

  @Mock
  InputPort inputPort;

  @InjectMocks
  GetGreetingController controller;

  @BeforeEach
  void setUp() {
    given(inputPort.getGreeting(anyString())).willReturn("OK");
  }

  @Test
  void test() {

    String name = "Test";

    ResponseEntity<GreetingDto> responseEntity =
        controller.getGreetingUsingGET(name);

    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals("OK", responseEntity.getBody().getMessage());
  }
}
