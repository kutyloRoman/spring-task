package com.kutylo.springtask.security;

import com.kutylo.springtask.model.User;
import com.kutylo.springtask.security.jwt.JwtUserFactory;
import com.kutylo.springtask.service.UserService;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Data
public class JwtUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.getByUsername(username);
    return JwtUserFactory.create(user);
  }
}
