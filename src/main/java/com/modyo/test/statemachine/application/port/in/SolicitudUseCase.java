package com.modyo.test.statemachine.application.port.in;

import com.modyo.test.statemachine.domain.model.Solicitud;
import java.util.List;

public interface SolicitudUseCase {

  List<Solicitud> getAll();

  Solicitud getOne(Long idSolicitud);

  Solicitud newSolicitud(String name);

  Solicitud processEvent(Long solicitudId, String eventName);
}
