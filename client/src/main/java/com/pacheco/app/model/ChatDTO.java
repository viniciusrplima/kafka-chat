package com.pacheco.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {

    private Long id;
    private String name;
    private String kafkaTopic;
    private List<UserDTO> users;

    public ChatDTO(String name) {
        this.name = name;
    }
}
