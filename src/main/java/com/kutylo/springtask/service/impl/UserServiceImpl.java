package com.kutylo.springtask.service.impl;

import com.kutylo.springtask.dto.request.UserRequest;
import com.kutylo.springtask.dto.response.UserResponse;
import com.kutylo.springtask.mapper.UserMapper;
import com.kutylo.springtask.model.User;
import com.kutylo.springtask.repository.UserRepository;
import com.kutylo.springtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

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
    userRepository.save(user);

    return userMapper.modelToResponse(user);

  }

  @Override
  public UserResponse update(int id, UserRequest userRequest) {
    User user = userMapper.requestToModel(userRequest);
    user.setId(id);

    userRepository.save(user);
    return userMapper.modelToResponse(user);
  }

  @Override
  public void delete(int id) {
    userRepository.deleteById(id);
  }
}
