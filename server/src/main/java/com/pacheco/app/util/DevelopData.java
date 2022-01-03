package com.pacheco.app.util;

import com.pacheco.app.dto.UserDTO;
import com.pacheco.app.model.Chat;
import com.pacheco.app.model.User;
import com.pacheco.app.repository.ChatRepository;
import com.pacheco.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DevelopData {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatRepository chatRepository;

    @PostConstruct
    public void setupData() {
        UserDTO user1 = new UserDTO();
        user1.setUsername("vinicius");
        user1.setPassword("123");

        UserDTO user2 = new UserDTO();
        user2.setUsername("joao");
        user2.setPassword("123");

        User savedUser1 = userService.registerUser(user1);
        User savedUser2 = userService.registerUser(user2);

        Chat chat1 = new Chat();
        chat1.setName("my annotations");
        chat1.setKafkaTopic("chat_64sd6d4sd888");
        chat1.setUsers(List.of(savedUser1));

        Chat chat2 = new Chat();
        chat2.setName("Amigos");
        chat2.setKafkaTopic("chat_ds54ds5d88d");
        chat2.setUsers(List.of(savedUser1, savedUser2));

        chatRepository.save(chat1);
        chatRepository.save(chat2);
    }

}
