package com.poc.jwt.Security.Filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.poc.jwt.Models.JWTResponse;
import com.poc.jwt.Security.JWT.JWTKeyGenerator;
import com.poc.jwt.Security.JWT.JWTSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JWTValidationFilter extends BasicAuthenticationFilter {

    JWTVerifier jwtVerifier;

    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authorizationHeaderContent = request.getHeader(JWTSettings.AUTHORIZATION_HEADER);

        if(authorizationHeaderContent == null || !authorizationHeaderContent.startsWith(JWTSettings.TOKEN_HEADER_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        DecodedJWT decodedJWT = validateAndDecodeToken(authorizationHeaderContent);

        if(decodedJWT == null) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write(JWTResponse.invalidToken());
            return;
        }

        List<SimpleGrantedAuthority> roles = decodedJWT.getClaim(JWTSettings.ROLES_CLAIM_NAME)
                .asList(SimpleGrantedAuthority.class);

        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(),
                null,
                roles);

        SecurityContextHolder.getContext().setAuthentication(user);

        chain.doFilter(request, response);

    }

    private DecodedJWT validateAndDecodeToken(String token) {

        token = token.replaceFirst(JWTSettings.TOKEN_HEADER_PREFIX, "");
        DecodedJWT decodedJWT;

        try {
            decodedJWT = jwtVerifier.verify(token);
        } catch (JWTVerificationException ex) {
            return null;
        }

        return decodedJWT;
    }

    public void setJwtVerifier(Algorithm algorithm) {
        jwtVerifier = JWT.require(algorithm).build();
    }
}
