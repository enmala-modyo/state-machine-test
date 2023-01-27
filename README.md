# Spring Statemachine demo - Persist

Esta aplicación es un pequeño ejemplo del uso de Spring Statemachine con persistencia JPA.

La configuración utilizada es muy simple, se utiliza la *"receta"* Persist para registrar en la BD los
cambios de estado el que se ejecutan.

Para la implementación de flujos simples debería ser suficiente.

En el ejemplo se debe notar que los cambios de estado se ejecutan en un método anotado como `@Transactional`.
Esto para tratar de evitar que dos instancias realicen cambios simultáneos en la máquina.
El registro en la BD de los cambios de estado se ejecuta utilizando un interceptor.
En el repositorio para la solicitud `SolicitudJpaRepository` se ha implementado el método findByIdLocked con la anotación `@Lock`
para impedir que otras instancias de la máquina puedan actuar sobre el registro durante un cambio de estado.
```
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<SolicitudJpaEntity> findByIdLocked(Long aLong);
```
Esta no es la configuración ideal, pues bloquea todas las lecturas de ese registro durante el cambio de estado,
pero por ahora es la única forma que he encontrado de garantizar que no se ejecuten cambios simultáneos.

La máquina implementada es la siguiente:
```mermaid
---
Máquina de Estados
---
stateDiagram-v2
  direction LR
  classDef pseudo fill:lightblue
  state if_state <<choice>>
  S1
  note left of S1
    on enter: A1
  end note
  note right of S1
    on exit: A2
  end note
    [*] --> S1 : E0
    S1 --> S2 : E1
    S1 --> S3 : E2
    S2 --> if_state
    if_state --> [*] : True
    if_state --> S3 : False
    S3 --> [*] : E4
    class S2 pseudo
```

- Las solicitudes comienzan en el estado inicial al momento de crearse.
- El estado S1 tiene asociada una acción de entrada y una de salida
- El estado S2 es un pseudo-estado de choice, esto quiere decir que cuando una solicitud llega a ese estado inmediatamente se evalúa la condición asociada y la máquina se mueve al estado resultante (S3 o estado final)
