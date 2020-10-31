package com.poc.jwt.Services;

import com.poc.jwt.Models.Authoritie;
import com.poc.jwt.Models.ROLES;
import com.poc.jwt.Models.User;
import com.poc.jwt.Repositories.AuthoritieRepository;
import com.poc.jwt.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOp = findUserByEmail(username);

        if(userOp.isEmpty())
            throw new UsernameNotFoundException("Email not Found.");

        User user = userOp.get();

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public User createUser(User user) {
        user.password = passwordEncoder.encode(user.password);
        user.addAuthorite(ROLES.USER);
        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

}
