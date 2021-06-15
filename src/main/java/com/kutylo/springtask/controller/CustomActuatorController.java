package com.kutylo.springtask.controller;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@RestControllerEndpoint(id = "test")
@Component
public class CustomActuatorController {

  @GetMapping("/")
  public ResponseEntity customEndPoint() {
    return new ResponseEntity<>("REST end point", HttpStatus.OK);
  }

}
