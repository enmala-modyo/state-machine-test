package com.modyo.test.statemachine.application.service.guards;

import com.modyo.ms.commons.statemachine.generic.AbstractGuard;
import com.modyo.test.statemachine.config.StateMachineConfig;
import com.modyo.test.statemachine.domain.model.Solicitud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component("s3Guard")
@Slf4j
public class S3Guard extends AbstractGuard<Solicitud> {
  @Override
  protected String getEntityHeaderName() {
    return StateMachineConfig.SM_ENTITY_HEADER;
  }

  @Override
  public boolean evaluate(StateContext<String, String> context) {
    return true;
  }
}
