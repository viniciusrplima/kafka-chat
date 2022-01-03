package com.pacheco.app.model.jwt;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtRequest {

    private String username;
    private String password;

}
