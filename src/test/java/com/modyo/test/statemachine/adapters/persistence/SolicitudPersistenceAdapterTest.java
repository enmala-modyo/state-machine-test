package com.modyo.test.statemachine.adapters.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.modyo.ms.commons.core.exceptions.NotFoundException;
import com.modyo.test.statemachine.domain.model.Estado;
import com.modyo.test.statemachine.domain.model.Solicitud;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SolicitudPersistenceAdapterTest {

  @Mock
  private SolicitudJpaRepository solicitudJpaRepository;

  @Mock
  private SolicitudMapper solicitudMapper;

  @InjectMocks
  SolicitudPersistenceAdapter solicitudPersistenceAdapter;

  @BeforeEach
  void setUp() {
  }

  @Test
  void testCreate() {
    SolicitudJpaEntity solicitudJpaEntity = new SolicitudJpaEntity();
    solicitudJpaEntity.setId(123L);
    solicitudJpaEntity.setName("Name");
    solicitudJpaEntity.setState(Estado.INIT);
    when(solicitudJpaRepository.save(any())).thenReturn(solicitudJpaEntity);
    Solicitud solicitud = new Solicitud();
    when(solicitudMapper.toEntity(any())).thenReturn(solicitud);
    assertEquals(solicitud, solicitudPersistenceAdapter.create("Name"));
    verify(solicitudJpaRepository).save(any());
    verify(solicitudMapper).toEntity(any());
  }

  @Test
  void testCreate2() {
    SolicitudJpaEntity solicitudJpaEntity = new SolicitudJpaEntity();
    solicitudJpaEntity.setId(123L);
    solicitudJpaEntity.setName("Name");
    solicitudJpaEntity.setState(Estado.INIT);
    when(solicitudJpaRepository.save(any())).thenReturn(solicitudJpaEntity);
    when(solicitudMapper.toEntity(any())).thenThrow(new NotFoundException());
    assertThrows(NotFoundException.class, () -> solicitudPersistenceAdapter.create("Name"));
    verify(solicitudJpaRepository).save(any());
    verify(solicitudMapper).toEntity(any());
  }

  @Test
  void testLoad() {
    SolicitudJpaEntity solicitudJpaEntity = new SolicitudJpaEntity();
    solicitudJpaEntity.setId(123L);
    solicitudJpaEntity.setName("Name");
    solicitudJpaEntity.setState(Estado.INIT);
    Optional<SolicitudJpaEntity> ofResult = Optional.of(solicitudJpaEntity);
    when(solicitudJpaRepository.findById(any())).thenReturn(ofResult);
    Solicitud solicitud = new Solicitud();
    when(solicitudMapper.toEntity(any())).thenReturn(solicitud);
    assertSame(solicitud, solicitudPersistenceAdapter.load(123L));
    verify(solicitudJpaRepository).findById(any());
    verify(solicitudMapper).toEntity(any());
  }

  @Test
  void testLoadAndLock() {
    SolicitudJpaEntity solicitudJpaEntity = new SolicitudJpaEntity();
    solicitudJpaEntity.setId(123L);
    solicitudJpaEntity.setName("Name");
    solicitudJpaEntity.setState(Estado.INIT);
    Optional<SolicitudJpaEntity> ofResult = Optional.of(solicitudJpaEntity);
    when(solicitudJpaRepository.findByIdLocked(any())).thenReturn(ofResult);
    when(solicitudMapper.toEntity(any())).thenReturn(new Solicitud());
    solicitudPersistenceAdapter.loadAndLock(123L);
    verify(solicitudJpaRepository).findByIdLocked(any());
    verify(solicitudMapper).toEntity(any());
  }

  @Test
  void testLoadAndLock_NotFound() {
    Optional<SolicitudJpaEntity> ofResult = Optional.empty();
    when(solicitudJpaRepository.findByIdLocked(any())).thenReturn(ofResult);
    assertThrows(NotFoundException.class, () -> solicitudPersistenceAdapter.loadAndLock(123L));
    verify(solicitudJpaRepository).findByIdLocked(any());
  }

  @Test
  void testLoadAllActive() {
    when(solicitudJpaRepository.findAllByStateNot(any())).thenReturn(new ArrayList<>());
    assertTrue(solicitudPersistenceAdapter.loadAllActive().isEmpty());
    verify(solicitudJpaRepository).findAllByStateNot(any());
  }

  @Test
  void testSave() {
    SolicitudJpaEntity solicitudJpaEntity = new SolicitudJpaEntity();
    solicitudJpaEntity.setId(123L);
    solicitudJpaEntity.setName("Name");
    solicitudJpaEntity.setState(Estado.INIT);
    when(solicitudJpaRepository.save(any())).thenReturn(solicitudJpaEntity);

    SolicitudJpaEntity solicitudJpaEntity1 = new SolicitudJpaEntity();
    solicitudJpaEntity1.setId(123L);
    solicitudJpaEntity1.setName("Name");
    solicitudJpaEntity1.setState(Estado.INIT);
    when(solicitudMapper.toJpaEntity(any())).thenReturn(solicitudJpaEntity1);
    solicitudPersistenceAdapter.save(new Solicitud());
    verify(solicitudJpaRepository).save(any());
    verify(solicitudMapper).toJpaEntity(any());
  }

}
