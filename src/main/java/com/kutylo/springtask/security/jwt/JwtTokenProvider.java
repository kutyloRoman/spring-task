package com.kutylo.springtask.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Data
@Component
@Slf4j
public class JwtTokenProvider {

  private final String BEARER = "Bearer_";

  @Value("${jwt.token.secret}")
  private String secret;

  @Value("${jwt.token.expired}")
  private long validityInMilliseconds;

  private final UserDetailsService userDetailsService;

  @PostConstruct
  protected void init() {
    secret = Base64.getEncoder().encodeToString(secret.getBytes());
  }

  public String createToken(String username, boolean hasAdminPermission) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("roles", hasAdminPermission ? JwtUserFactory.ADMIN_ROLE : JwtUserFactory.USER_ROLE);

    Date nowDate = new Date();
    Date validity = new Date(nowDate.getTime() + validityInMilliseconds);

    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(validity)
        .setIssuedAt(nowDate)
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest request) {
    log.info("Resolving token!");
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith(BEARER)) {
      return bearerToken.substring(BEARER.length());
    }
    log.info("Resolving token return null!");
    return null;
  }

  public boolean validateToken(String token) {
    log.info("Validate token");
    try {
      Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return !claimsJws.getBody().getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      log.warn("Token is not valid!");
      return false;
    }
  }
}
