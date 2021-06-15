package com.kutylo.springtask.controller;

import com.kutylo.springtask.dto.request.UserRequest;
import com.kutylo.springtask.dto.response.AuthenticationResponse;
import com.kutylo.springtask.dto.response.UserResponse;
import com.kutylo.springtask.service.AuthenticationService;
import com.kutylo.springtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

  private final AuthenticationService authenticationService;
  private final UserService userService;

  @PostMapping("/login")
  public AuthenticationResponse login(@RequestBody UserRequest userRequest) {
    return authenticationService.login(userRequest);
  }

  @PostMapping("/register")
  public UserResponse register(@RequestBody UserRequest userRequest) {
    return userService.save(userRequest);
  }
}
