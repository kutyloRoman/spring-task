package com.kutylo.springtask.security.jwt;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class JwtTokenFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
    if (token != null && jwtTokenProvider.validateToken(token)) {
      Authentication authentication = jwtTokenProvider.getAuthentication(token);
      if (authentication != null) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } else {
        log.warn("Authentication failed!");
      }
    }
    chain.doFilter(request, response);
  }
}
