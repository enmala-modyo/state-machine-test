package com.modyo.test.statemachine.application.port.out;

import com.modyo.test.statemachine.domain.model.Solicitud;

public interface SaveSolicitudPort {
  void save(Solicitud solicitud);

}
