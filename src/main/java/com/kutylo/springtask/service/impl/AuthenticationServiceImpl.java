package com.kutylo.springtask.service.impl;

import com.kutylo.springtask.dto.request.UserRequest;
import com.kutylo.springtask.dto.response.AuthenticationResponse;
import com.kutylo.springtask.model.User;
import com.kutylo.springtask.security.jwt.JwtTokenProvider;
import com.kutylo.springtask.service.AuthenticationService;
import com.kutylo.springtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserService userService;
  private BCryptPasswordEncoder encoder;

  @PostConstruct
  private void initEncoder() {
    encoder = new BCryptPasswordEncoder();
  }

  @Override
  public AuthenticationResponse login(UserRequest userRequest) {
    User user = userService.getByUsername(userRequest.getUsername());

    if (!encoder.matches(userRequest.getPassword(), user.getPassword())) {
      throw new IllegalArgumentException("Password not correct");
    }

    String token = jwtTokenProvider.createToken(user.getUsername(), user.isAdmin());
    return new AuthenticationResponse(user.getUsername(), token);
  }

}
