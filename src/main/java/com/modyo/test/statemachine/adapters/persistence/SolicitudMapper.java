package com.modyo.test.statemachine.adapters.persistence;

import com.modyo.test.statemachine.domain.model.Solicitud;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    componentModel = "spring")
public interface SolicitudMapper {

  Solicitud toEntity(SolicitudJpaEntity jpaEntity);

  SolicitudJpaEntity toJpaEntity(Solicitud solicitud);
}
