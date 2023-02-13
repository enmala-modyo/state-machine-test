package com.modyo.test.statemachine.application.service.guards;

import com.modyo.ms.commons.statemachine.generic.AbstractGuard;
import com.modyo.test.statemachine.application.port.out.LoadRandomAnswerPort;
import com.modyo.test.statemachine.domain.model.Estado;
import com.modyo.test.statemachine.domain.model.Evento;
import com.modyo.test.statemachine.domain.model.Solicitud;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component("evaluateExternalConditionGuard")
@RequiredArgsConstructor
@Slf4j
public class EvaluateExternalConditionGuard extends AbstractGuard<Solicitud, Estado, Evento> {

  private final LoadRandomAnswerPort answerPort;

  @Override
  public boolean evaluate(StateContext<Estado, Evento> context) {
    var result = answerPort.getAnswer();
    log.info("Solicitud {} - rule evaluation: {}", this.getEntity(context).getId(), result);
    return result;
  }

}
