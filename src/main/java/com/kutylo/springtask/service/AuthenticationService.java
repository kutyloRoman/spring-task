package com.kutylo.springtask.service;

import com.kutylo.springtask.dto.request.UserRequest;
import com.kutylo.springtask.dto.response.AuthenticationResponse;

public interface AuthenticationService {
  AuthenticationResponse login(UserRequest userRequest);
}