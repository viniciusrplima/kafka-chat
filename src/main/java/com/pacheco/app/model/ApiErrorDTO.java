package com.pacheco.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApiErrorDTO {

    private String timestamp;

    private Integer status;

    private String error;

    private String trace;

    private String message;

    private String path;

}
