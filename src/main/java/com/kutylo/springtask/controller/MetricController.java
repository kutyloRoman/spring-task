package com.kutylo.springtask.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/metrics")
public class MetricController {
  @Autowired
  private MeterRegistry meterRegistry;

  @RequestMapping(value = "/message/{message}", method = RequestMethod.GET)
  public String getMessage(@PathVariable("message") String message) {

    // counter to count different types of messages received
    meterRegistry.counter("custom.metrics.message", "value", message).increment();

    return message;
  }
}

