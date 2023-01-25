package com.modyo.test.statemachine.adapters.web;

import com.modyo.test.statemachine.adapters.web.dto.Response;
import com.modyo.test.statemachine.adapters.web.dto.SolicitudDto;
import com.modyo.test.statemachine.adapters.web.dto.SolicitudDtoMapper;
import com.modyo.test.statemachine.application.port.in.SolicitudUseCase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"statemachine"})
@RequiredArgsConstructor
public class StateMachineController {

  private final SolicitudUseCase service;
  private final SolicitudDtoMapper mapper;

  @ApiOperation(
      value = "getAllSolicitudes")
  @GetMapping(
      value = "/statemachine/solicitudes",
      produces = "application/json")
  public ResponseEntity<Response<List<SolicitudDto>>> getSolicitudes() {
    List<SolicitudDto> solicitudes = service.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
    return ResponseEntity.ok(new Response<List<SolicitudDto>>(solicitudes));
  }

  @ApiOperation(
      value = "getSolicitud")
  @GetMapping(
      value = "/statemachine/solicitudes/{idSolicitud}",
      produces = "application/json")
  public ResponseEntity<Response<SolicitudDto>> getSolicitud(
      @PathVariable("idSolicitud") Long idSolicitud
  ) {
    return ResponseEntity.ok(new Response<SolicitudDto>(mapper.toDto(service.getOne(idSolicitud))));
  }

  @ApiOperation(
      value = "createSolicitud")
  @PostMapping(
      value = "/statemachine/solicitudes",
      produces = "application/json")
  public ResponseEntity<Response<SolicitudDto>> createSolicitud(
      @RequestParam String name
  ) {
    SolicitudDto solicitud = mapper.toDto(service.newSolicitud(name));
    return ResponseEntity.ok(new Response<SolicitudDto>(solicitud));
  }

  @ApiOperation(
      value = "updateSolicitud")
  @PatchMapping(
      value = "/statemachine/solicitudes/{idSolicitud}/{event}",
      produces = "application/json")
  public ResponseEntity<Response<SolicitudDto>> updateSolicitud(
      @PathVariable Long idSolicitud,
      @PathVariable String event
  ) {
    SolicitudDto solicitud = mapper.toDto(service.processEvent(idSolicitud, event));
    return ResponseEntity.ok(new Response<SolicitudDto>(solicitud));
  }
}
