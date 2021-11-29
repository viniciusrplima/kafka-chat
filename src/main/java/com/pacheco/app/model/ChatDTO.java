package com.pacheco.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {

    private Long id;

    private String name;

    private String kafkaTopic;

    public ChatDTO(String name) {
        this.name = name;
    }
}
