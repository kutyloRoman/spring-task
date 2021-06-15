package com.kutylo.springtask.controller;

import com.kutylo.springtask.dto.request.UserRequest;
import com.kutylo.springtask.dto.response.UserResponse;
import com.kutylo.springtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  List<UserResponse> getAllUsers() {
    return userService.getAllUsers();
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  UserResponse getUserById(@PathVariable int id) {
    return userService.getUserById(id);
  }

  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.OK)
  UserResponse update(@PathVariable int id, @RequestBody UserRequest userRequest) {
    return userService.update(id, userRequest);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.DELETE
  )
  void delete(@PathVariable int id) {
    userService.delete(id);
  }

}
