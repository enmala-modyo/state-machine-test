package com.modyo.test.statemachine.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Solicitud {
  private Long id;
  private String state;
  private String name;
}
