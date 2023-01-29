package com.modyo.test.statemachine.adapters.persistence;

import com.modyo.test.statemachine.domain.statemachine.StatesEnum;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface SolicitudJpaRepository extends JpaRepository<SolicitudJpaEntity,Long> {

  List<SolicitudJpaEntity> findAllByStateNot(StatesEnum state);


  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select s from SolicitudJpaEntity s where s.id = ?1")
  Optional<SolicitudJpaEntity> findByIdLocked(Long aLong);
}
