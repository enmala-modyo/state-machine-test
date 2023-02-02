package com.modyo.test.statemachine.adapters.persistence;

import com.modyo.ms.commons.core.exceptions.NotFoundException;
import com.modyo.test.statemachine.application.port.out.CreateSolicitudPort;
import com.modyo.test.statemachine.application.port.out.LoadSolicitudPort;
import com.modyo.test.statemachine.application.port.out.SaveSolicitudPort;
import com.modyo.test.statemachine.domain.enums.States;
import com.modyo.test.statemachine.domain.model.Solicitud;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SolicitudPersistenceAdapter implements LoadSolicitudPort, CreateSolicitudPort, SaveSolicitudPort {

  private final SolicitudJpaRepository repository;
  private final SolicitudMapper mapper;


  @Override
  public Solicitud create(String name) {
    var jpaEntity = SolicitudJpaEntity.builder().name(name).state(States.SI).build();
    return mapper.toEntity(repository.save(jpaEntity));
  }

  @Override
  public Solicitud load(Long id) {
    var jpaEntity = repository.findById(id);
    return mapper.toEntity(jpaEntity.orElseThrow(NotFoundException::new));
  }

  @Override
  public Solicitud loadAndLock(Long id) {
    var jpaEntity = repository.findByIdLocked(id);
    return mapper.toEntity(jpaEntity.orElseThrow(NotFoundException::new));
  }

  @Override
  public List<Solicitud> loadAllActive(){
    var allActive = repository.findAllByStateNot(States.SF);
    return allActive.stream().map(mapper::toEntity).collect(Collectors.toList());
  }

  @Override
  public void save(Solicitud solicitud) {
    repository.save(mapper.toJpaEntity(solicitud));
  }
}
