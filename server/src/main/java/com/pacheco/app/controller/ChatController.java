package com.pacheco.app.controller;

import com.pacheco.app.dto.ChatDTO;
import com.pacheco.app.model.Chat;
import com.pacheco.app.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public List<Chat> getChats() {
        return chatService.getUserChats();
    }

    @PostMapping
    public Chat saveChat(@RequestBody @Valid ChatDTO chat) {
        return chatService.saveChat(chat);
    }

    @PostMapping("/{chatId}")
    public Chat joinChat(@PathVariable Long chatId) {
        return chatService.joinChat(chatId);
    }

}
