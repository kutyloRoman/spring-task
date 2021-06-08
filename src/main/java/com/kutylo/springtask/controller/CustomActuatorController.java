package com.kutylo.springtask.controller;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestControllerEndpoint(id = "custom")
public class CustomActuatorController {

  @GetMapping("/")
  public ResponseEntity customEndPoint() {
    return new ResponseEntity<>("REST end point", HttpStatus.OK);
  }

}
