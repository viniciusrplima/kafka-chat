package com.pacheco.app.service;

import com.pacheco.app.dto.UserDTO;
import com.pacheco.app.exception.UserNotFoundException;
import com.pacheco.app.model.User;
import com.pacheco.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findUserByUsername(String username) throws UserNotFoundException {
        return repository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Transactional
    public User registerUser(UserDTO userDto) {
        String passwordEncoded = passwordEncoder.encode(userDto.getPassword());

        User user = new User();
        user.setPassword(passwordEncoded);
        user.setUsername(userDto.getUsername());

        return repository.save(user);
    }

}
