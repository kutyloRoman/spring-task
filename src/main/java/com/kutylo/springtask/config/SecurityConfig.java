package com.kutylo.springtask.config;

import com.kutylo.springtask.security.jwt.JwtConfigurer;
import com.kutylo.springtask.security.jwt.JwtTokenProvider;
import com.kutylo.springtask.security.jwt.JwtUserFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EqualsAndHashCode(callSuper = true)
@Configuration
@Data
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenProvider jwtTokenProvider;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .httpBasic().disable()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET).permitAll()
        .antMatchers("/auth/login").permitAll()
        .antMatchers("/auth/register").permitAll()
        .antMatchers(HttpMethod.POST).hasAuthority(JwtUserFactory.ADMIN_ROLE)
        .antMatchers(HttpMethod.PUT).hasAuthority(JwtUserFactory.ADMIN_ROLE)
        .antMatchers(HttpMethod.DELETE).hasAuthority(JwtUserFactory.ADMIN_ROLE)
        .anyRequest().authenticated()
        .and()
        .apply(new JwtConfigurer(jwtTokenProvider));
  }
}