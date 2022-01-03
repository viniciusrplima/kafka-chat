package com.pacheco.app.controller;

import com.pacheco.app.model.User;
import com.pacheco.app.model.jwt.JwtRequest;
import com.pacheco.app.model.jwt.JwtResponse;
import com.pacheco.app.service.AuthenticationService;
import com.pacheco.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public User registerUser(@RequestBody @Valid User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public JwtResponse loginUser(@RequestBody JwtRequest jwtRequest) {
        return authenticationService.authenticate(jwtRequest);
    }

}
