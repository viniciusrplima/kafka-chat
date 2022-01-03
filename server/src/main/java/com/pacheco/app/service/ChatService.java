package com.pacheco.app.service;

import com.pacheco.app.dto.ChatDTO;
import com.pacheco.app.exception.ChatNotFoundException;
import com.pacheco.app.model.Chat;
import com.pacheco.app.model.User;
import com.pacheco.app.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pacheco.app.util.AuthenticationUtils.currentUsername;
import static com.pacheco.app.util.HashGenerator.generateHashedName;

@Service
public class ChatService {

    public static final String CHAT_TOPIC_PREFIX = "chat_";
    public static final int CHAT_TOPIC_HASH_SIZE = 16;

    @Autowired
    private ChatRepository repository;

    @Autowired
    private UserService userService;

    public List<Chat> getUserChats() {
        return userService.findUserByUsername(currentUsername()).getChats();
    }

    private Chat getChat(Long chatId) {
        return repository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException(chatId));
    }

    public Chat saveChat(ChatDTO chatDto) {
        User user = userService.findUserByUsername(currentUsername());

        Chat newChat = new Chat();
        newChat.setName(chatDto.getName());
        newChat.setKafkaTopic(generateHashedName(CHAT_TOPIC_PREFIX, CHAT_TOPIC_HASH_SIZE));
        newChat.setUsers(List.of(user));

        return repository.save(newChat);
    }

    public Chat joinChat(Long chatId) {
        Chat chat = getChat(chatId);
        User user = userService.findUserByUsername(currentUsername());

        chat.getUsers().add(user);
        return repository.save(chat);
    }

}
