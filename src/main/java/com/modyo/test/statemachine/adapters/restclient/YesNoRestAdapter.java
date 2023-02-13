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
      var response = client.getResponse();
      if(response.getBody()!=null){
        answer = "yes".equals(response.getBody().getAnswer());
      } else {
        answer = false;
      }
    } catch (Exception e) {
      answer = false;
    }
    return answer;
  }
}
