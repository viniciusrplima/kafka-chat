package com.pacheco.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Long id;

    private String username;

    private String password;

    private List<ChatDTO> chats;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
