package com.modyo.test.statemachine.adapters.web;

import com.modyo.test.statemachine.adapters.web.dto.GreetingDto;
import com.modyo.test.statemachine.application.port.in.InputPort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"example"})
@RestController
@RequiredArgsConstructor
public class GetGreetingController {

  private final InputPort port;

  @ApiOperation(
      value = "getGreeting",
      nickname = "getGreetingUsingGET",
      response = GreetingDto.class)
  @RequestMapping(
      value = "/greeting",
      method = RequestMethod.GET,
      produces = "application/json")
  public ResponseEntity<GreetingDto> getGreetingUsingGET(
      @RequestParam(value = "name", defaultValue = "World", required = false) String name
  ) {
    var response = GreetingDto.builder().message(port.getGreeting(name)).build();
    return ResponseEntity.ok(response);
  }
}
