package com.kutylo.springtask.service.impl;

import com.kutylo.springtask.dto.request.UserRequest;
import com.kutylo.springtask.dto.response.UserResponse;
import com.kutylo.springtask.mapper.UserMapper;
import com.kutylo.springtask.model.User;
import com.kutylo.springtask.repository.UserRepository;
import com.kutylo.springtask.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private BCryptPasswordEncoder encoder;

  @PostConstruct
  private void initEncoder() {
    encoder = new BCryptPasswordEncoder();
  }

  @Override
  public User getByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("Not found entity with username:" + username));
  }

  @Override
  public List<UserResponse> getAllUsers() {
    return userRepository.findAll()
        .stream()
        .map(userMapper::modelToResponse)
        .collect(Collectors.toList());
  }

  @Override
  public UserResponse getUserById(int id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Not found entity with id:" + id));
    return userMapper.modelToResponse(user);
  }

  @Override
  public UserResponse save(UserRequest userRequest) {
    User user = userMapper.requestToModel(userRequest);
    user.setPassword(encoder.encode(userRequest.getPassword()));
    userRepository.save(user);

    return userMapper.modelToResponse(user);

  }

  @Override
  public UserResponse update(int id, UserRequest userRequest) {
    User user = userMapper.requestToModel(userRequest);
    user.setPassword(encoder.encode(userRequest.getPassword()));
    user.setId(id);

    userRepository.save(user);
    return userMapper.modelToResponse(user);
  }

  @Override
  public void delete(int id) {
    userRepository.deleteById(id);
  }
}
