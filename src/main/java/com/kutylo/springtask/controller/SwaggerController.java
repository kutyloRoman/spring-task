package com.kutylo.springtask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {
  private static final String REDIRECT_SWAGGER_UI = "redirect:/swagger-ui.html";

  @GetMapping(value = {"", "/swagger"})
  public String redirectSwagger() {
    return REDIRECT_SWAGGER_UI;
  }
}
