package com.poc.jwt.Security.Config;

import com.poc.jwt.Security.Handlers.JWTAccessDeniedHandler;
import com.poc.jwt.Security.Handlers.JWTAuthenticationFailureHandler;
import com.poc.jwt.Security.Filters.JWTAuthenticationFilter;
import com.poc.jwt.Security.Filters.JWTValidationFilter;
import com.poc.jwt.Security.JWT.JWTKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JWTKeyGenerator JWTKeyGenerator;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().csrf().disable()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/auth").permitAll()
                .antMatchers("/free").permitAll()
                .anyRequest().authenticated().and()
                .addFilter(authenticationFilter())
                .addFilter(validationFilter())
                .exceptionHandling().accessDeniedHandler(new JWTAccessDeniedHandler()).and()
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().formLogin().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if(rawPassword.toString().compareTo(encodedPassword.toString()) == 0)
                    return true;
                return false;
            }
        };
    }

    @Bean
    JWTAuthenticationFilter authenticationFilter() throws Exception {
        JWTAuthenticationFilter JWTAuthenticationFilter = new JWTAuthenticationFilter();
        JWTAuthenticationFilter.setAuthenticationManager(authenticationManager());
        JWTAuthenticationFilter.setFilterProcessesUrl("/api/auth");
        JWTAuthenticationFilter.setPostOnly(true);
        //JWTAuthenticationFilter.setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        return JWTAuthenticationFilter;
    }

    JWTValidationFilter validationFilter() throws Exception {
        JWTValidationFilter JWTValidationFilter = new JWTValidationFilter(authenticationManager());
        JWTValidationFilter.setJwtVerifier(JWTKeyGenerator.getAlgorithm());
        return JWTValidationFilter;
    }
}
