package com.pacheco.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthDTO {

    @JsonProperty("jwttoken")
    private String jwtToken;

}
