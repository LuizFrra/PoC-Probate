package com.poc.jwt.Security.Filters;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.jwt.Models.JWTResponse;
import com.poc.jwt.Models.LoginRequest;
import com.poc.jwt.Security.JWT.JWTSettings;
import com.poc.jwt.Security.JWT.JWTKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    JWTKeyGenerator JWTkeyGenerator;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        if(!request.getMethod().equals("POST"))
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());

        String body;

        LoginRequest loginRequest;

        try {
            body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            loginRequest = new ObjectMapper().readValue(body, LoginRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationServiceException("Authentication failed on parse request Body");
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                loginRequest.username.trim(), loginRequest.password
        );

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        int minutesToExpire = JWTSettings.TOKEN_EXPIRATION_TIME_IN_MINUTES;

        Instant utcNow = Instant.now();
        Instant expires = utcNow.plus(minutesToExpire, ChronoUnit.MINUTES);

        User user = (User)authResult.getPrincipal();

        Collection<GrantedAuthority> grantedAuthorities = user.getAuthorities();
        List<String> authorities = new ArrayList<>();
        for(GrantedAuthority authority : grantedAuthorities) {
            authorities.add(authority.getAuthority());
        }

        String token = JWT.create()
                .withSubject(((User)authResult.getPrincipal()).getUsername())
                .withClaim(JWTSettings.ROLES_CLAIM_NAME, authorities)
                .withExpiresAt(Date.from(expires))
                .withNotBefore(Date.from(utcNow))
                .withIssuedAt(Date.from(utcNow))
                .withIssuer(JWTSettings.ISSUER)
                .withJWTId(UUID.randomUUID().toString().replaceAll("-", ""))
                .sign(JWTkeyGenerator.getAlgorithm());

        response.setHeader(JWTSettings.AUTHORIZATION_HEADER, JWTSettings.TOKEN_HEADER_PREFIX + token);
        response.getWriter().write(JWTResponse.succesfullLogin(token));
        response.setContentType("application/json");
    }

}
