package com.poc.jwt.Controllers;

import com.poc.jwt.Models.User;
import com.poc.jwt.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User userSaved = userService.createUser(user);
        return new ResponseEntity<>(userSaved, HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public User getByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email).get();
    }
}
