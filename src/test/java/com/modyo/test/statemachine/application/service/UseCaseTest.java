package com.modyo.test.statemachine.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

import com.modyo.test.statemachine.application.port.out.OutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UseCaseTest {

  @Mock
  OutputPort port;
  @InjectMocks
  UseCase useCase;

  @BeforeEach
  void setUp() {
    given(port.loadGreeting(anyInt())).willReturn("Hello");
  }

  @Test
  void getGreeting() {
    String response = useCase.getGreeting("World");
    assertEquals("Hello World!!!", response);
  }
}
