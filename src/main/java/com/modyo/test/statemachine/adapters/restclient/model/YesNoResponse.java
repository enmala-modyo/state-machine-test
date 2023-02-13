package com.modyo.test.statemachine.adapters.restclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YesNoResponse {
  private String answer;
  private String forced;
  @JsonProperty("image")
  private String imageUrl;
}
