package com.kutylo.springtask.service;

import com.kutylo.springtask.dto.request.UserRequest;
import com.kutylo.springtask.dto.response.UserResponse;
import com.kutylo.springtask.mapper.UserMapper;
import com.kutylo.springtask.model.User;
import com.kutylo.springtask.repository.UserRepository;
import com.kutylo.springtask.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class UserServiceTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @Mock
  private BCryptPasswordEncoder encoder;

  private final UserRequest userRequest = new UserRequest(
      "username",
      "password"
  );

  private final User user = new User(
      1,
      "username",
      "password",
      false);

  private final UserResponse userResponse = new UserResponse(
      1,
      "username",
      "password");

  @Before
  public void setUpMocks() {
    MockitoAnnotations.openMocks(this);

    when(userMapper.requestToModel(Mockito.any(UserRequest.class))).thenAnswer(
        invocation -> {
          UserRequest userRequest = invocation.getArgument(0);
          return new User(
              1,
              userRequest.getUsername(),
              userRequest.getPassword(),
              false
          );
        }
    );

    when(userMapper.modelToResponse(Mockito.any(User.class))).thenAnswer(
        invocation -> {
          User user = invocation.getArgument(0);
          return new UserResponse(
              user.getId(),
              user.getUsername(),
              user.getPassword()
          );
        }
    );

    when(encoder.encode(user.getPassword())).thenReturn("password");
  }

  @Test
  public void getAllUsersTest() {
    when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

    List<UserResponse> users = userService.getAllUsers();

    assertTrue(users.size() > 0);
    assertEquals(users.get(0).getId(), user.getId());
    assertEquals(users.get(0).getUsername(), user.getUsername());
    assertEquals(users.get(0).getPassword(), user.getPassword());
  }

  @Test
  public void getUserById() {
    int id = 1;

    when(userRepository.findById(id))
        .thenAnswer(invocationOnMock -> {
          User answer = user;
          answer.setId(invocationOnMock.getArgument(0));
          return Optional.of(answer);
        });

    UserResponse actual = userService.getUserById(id);

    assertNotNull(actual);
    assertEquals(actual.getId(), id);
    assertEquals(actual.getUsername(), user.getUsername());
  }

  @Test
  public void saveUserTest() {
    int id = 1;

    when(userRepository.save(Mockito.any(User.class)))
        .thenAnswer(invocationOnMock -> {
          User answer = invocationOnMock.getArgument(0);
          answer.setId(id);
          return answer;
        });

    UserResponse actual = userService.save(userRequest);

    assertNotNull(actual);
    assertEquals(actual.getId(), id);
    assertEquals(actual.getUsername(), user.getUsername());
  }
}
