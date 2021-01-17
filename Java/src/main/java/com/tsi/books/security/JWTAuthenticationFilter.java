package com.tsi.books.security;

import static com.tsi.books.security.Constants.HEADER_AUTHORIZACION_KEY;
import static com.tsi.books.security.Constants.ISSUER_INFO;
import static com.tsi.books.security.Constants.SUPER_SECRET_KEY;
import static com.tsi.books.security.Constants.TOKEN_BEARER_PREFIX;
import static com.tsi.books.security.Constants.TOKEN_EXPIRATION_TIME;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response)
      throws AuthenticationException {
    try {
      ServletInputStream httpRequest = request.getInputStream();

      com.tsi.books.models.User credenciales = new ObjectMapper()
          .readValue(httpRequest, com.tsi.books.models.User.class);

      return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          credenciales.getNick(), credenciales.getPassword(), new ArrayList<>()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain,
      Authentication auth) {

    String token = Jwts.builder()
        .setIssuedAt(new Date()).setIssuer(ISSUER_INFO)
        .setSubject(((User) auth.getPrincipal()).getUsername())
        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, SUPER_SECRET_KEY).compact();
    response.addHeader(HEADER_AUTHORIZACION_KEY, TOKEN_BEARER_PREFIX + token);
  }
}