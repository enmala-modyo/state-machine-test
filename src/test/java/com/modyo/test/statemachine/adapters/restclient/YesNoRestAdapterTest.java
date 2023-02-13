package com.modyo.test.statemachine.adapters.restclient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.modyo.test.statemachine.adapters.restclient.model.YesNoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class YesNoRestAdapterTest {
  YesNoRestClient client = mock(YesNoRestClient.class);
  YesNoResponse response = new YesNoResponse();

  YesNoRestAdapter adapter = new YesNoRestAdapter(client);

  @BeforeEach
  void setUp() {
    when(client.getResponse()).thenReturn(ResponseEntity.ok(response));
  }

  @Test
  void getAnswerOfYes() {
    response.setAnswer("yes");
    assertTrue(adapter.getAnswer());
  }

  @Test
  void getAnswerOfNo() {
    response.setAnswer("no");
    assertFalse(adapter.getAnswer());
  }

  @Test
  void getAnswerOfNoData() {
    response = null;
    assertFalse(adapter.getAnswer());
  }

  @Test
  void getAnswerOfNoBody() {
    when(client.getResponse()).thenReturn(ResponseEntity.ok().build());
    assertFalse(adapter.getAnswer());
  }
}
