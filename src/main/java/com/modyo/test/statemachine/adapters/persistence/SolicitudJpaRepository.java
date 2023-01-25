package com.modyo.test.statemachine.adapters.persistence;

import com.modyo.test.statemachine.config.statemachine.StatesEnum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudJpaRepository extends JpaRepository<SolicitudJpaEntity,Long> {

  List<SolicitudJpaEntity> findAllByStateNot(StatesEnum state);

}
