package com.kutylo.springtask.security.jwt;

import com.kutylo.springtask.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

public class JwtUserFactory {

  public static final String ADMIN_ROLE = "ADMIN";
  public static final String USER_ROLE = "USER";

  public static JwtUser create(User user) {
    return new JwtUser(
        user.getId(), user.getUsername(), user.getPassword(), mapToGrantedAuthorities(user.isAdmin())
    );
  }

  private static List<GrantedAuthority> mapToGrantedAuthorities(boolean isAdmin) {
    SimpleGrantedAuthority authority;
    if (isAdmin) {
      authority = new SimpleGrantedAuthority(ADMIN_ROLE);
    } else {
      authority = new SimpleGrantedAuthority(USER_ROLE);
    }
    return Collections.singletonList(authority);
  }
}
