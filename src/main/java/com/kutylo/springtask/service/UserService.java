package com.kutylo.springtask.service;

import com.kutylo.springtask.dto.request.UserRequest;
import com.kutylo.springtask.dto.response.UserResponse;

import java.util.List;

public interface UserService {
  List<UserResponse> getAllUsers();

  UserResponse getUserById(int id);

  UserResponse save(UserRequest userRequest);

  UserResponse update(int id, UserRequest userRequest);

  void delete(int id);
}
