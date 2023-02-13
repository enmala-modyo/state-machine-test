package com.modyo.test.statemachine.adapters.restclient;

import com.modyo.test.statemachine.application.port.out.LoadRandomAnswerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class YesNoRestAdapter implements LoadRandomAnswerPort {
  private final YesNoRestClient client;

  @Override
  public Boolean getAnswer() {
    boolean answer;
    try{
      var response = client.getResponse().getBody();
      if(response!=null){
        answer = "yes".equals(response.getAnswer());
      } else {
        answer = false;
      }
    } catch (Exception e) {
      answer = false;
    }
    return answer;
  }
}
