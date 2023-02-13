package com.modyo.test.statemachine.adapters.restclient;

import com.modyo.test.statemachine.adapters.restclient.model.YesNoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "yesNoClient", url = "${datasources.rest.webServices.yesno.base-url}")
public interface YesNoRestClient {

  @GetMapping(value = "/")
  ResponseEntity<YesNoResponse> getResponse();
}
