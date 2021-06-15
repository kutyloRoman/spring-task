package com.kutylo.springtask.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kutylo.springtask.dto.request.UserRequest;
import com.kutylo.springtask.dto.response.UserResponse;
import com.kutylo.springtask.model.User;
import com.kutylo.springtask.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserRepository userRepository;

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

  @Test
  public void getAllUserTest() throws Exception {
    Mockito.when(userRepository.findAll())
        .thenReturn(Collections.singletonList(user));

    mockMvc.perform(get("/users/")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].id").isNotEmpty())
        .andExpect(jsonPath("$.[0].username").value(user.getUsername()))
        .andExpect(jsonPath("$.[0].password").value(user.getPassword()));
  }

  @Test
  public void getUserByIdTest() throws Exception {
    int id = 666;

    Mockito.when(userRepository.findById(id))
        .thenAnswer(invocation -> {
          User answer = user;
          answer.setId(invocation.getArgument(0));
          return Optional.of(answer);
        });

    mockMvc.perform(get("/users/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.username").value(user.getUsername()))
        .andExpect(jsonPath("$.password").value(user.getPassword()));
  }

  @Test
  public void updateTest() throws Exception {
    int id = 45;

    Mockito.when(userRepository.save(Mockito.any(User.class)))
        .thenAnswer(invocation -> {
          User answer = invocation.getArgument(0);
          answer.setId(id);
          return answer;
        });

    mockMvc.perform(put("/users/{id}", id)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsBytes(userRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.username").value(userRequest.getUsername()))
        .andExpect(jsonPath("$.password").isNotEmpty());
  }
}
