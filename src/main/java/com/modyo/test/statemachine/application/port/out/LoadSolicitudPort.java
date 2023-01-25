package com.modyo.test.statemachine.application.port.out;

import com.modyo.test.statemachine.domain.model.Solicitud;
import java.util.List;

public interface LoadSolicitudPort {
  Solicitud load(Long id);

  List<Solicitud> loadAllActive();
}
