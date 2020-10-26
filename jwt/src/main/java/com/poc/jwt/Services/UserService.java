package com.poc.jwt.Services;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if(username.compareTo("admin") == 0)
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        User user = new User(UUID.randomUUID().toString(), "foo", authorities);

        return user;
    }

}
