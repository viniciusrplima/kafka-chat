package com.pacheco.app.service;

import com.pacheco.app.config.jwt.JwtTokenUtil;
import com.pacheco.app.exception.BusinessException;
import com.pacheco.app.model.jwt.JwtRequest;
import com.pacheco.app.model.jwt.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    public JwtResponse authenticate(JwtRequest jwtRequest) throws BusinessException {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword());
            authenticationManager.authenticate(authToken);

            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
            String token = jwtTokenUtil.generateToken(userDetails);

            return new JwtResponse(token);
        } catch (DisabledException e) {
            throw new BusinessException("User disabled");
        } catch (BadCredentialsException e) {
            throw new BusinessException("Invalid credentials");
        }
    }
}
