package com.pacheco.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacheco.app.model.ApiErrorDTO;
import com.pacheco.app.model.AuthDTO;
import com.pacheco.app.model.ChatDTO;
import com.pacheco.app.model.UserDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class ChatServer {

    private static final String BASE_URL = "http://localhost:3000";

    public void register(String username, String password) throws IOException, InterruptedException {
        UserDTO userDTO = post("/register", new UserDTO(username, password), UserDTO.class);
    }

    public String login(String username, String password) throws IOException, InterruptedException {
        AuthDTO authDTO = post("/auth", new UserDTO(username, password), AuthDTO.class);
        return authDTO.getJwtToken();
    }

    public ChatDTO createChat(String name, String token) throws IOException, InterruptedException {
        ChatDTO chat = post("/chat", new ChatDTO(name), ChatDTO.class, token);
        return chat;
    }

    public List<ChatDTO> listChats(String token) throws IOException, InterruptedException {
        ChatDTO[] chats = get("/chat", ChatDTO[].class, token);
        return Arrays.asList(chats);
    }

    public ChatDTO joinChat(int chatCode, String token) throws IOException, InterruptedException {
        ChatDTO chat = post(String.format("/chat/%d", chatCode), null, ChatDTO.class, token);
        return chat;
    }

    private <T> String toJson(T object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    private <T> T toObject(String json, Class<T> classObj) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, classObj);
    }

    private <T, R> R post(String path, T body, Class<R> responseClass)
            throws IOException, InterruptedException, ApiErrorException {
        return post(path, body, responseClass, "");
    }

    private <T> T get(String path, Class<T> responseClass, String token) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Authorization", token)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 300) {
            ApiErrorDTO error = toObject(response.body(), ApiErrorDTO.class);
            throw new ApiErrorException(error.getMessage());
        }

        return toObject(response.body(), responseClass);
    }

    private <T, R> R post(String path, T body, Class<R> responseClass, String token)
            throws IOException, InterruptedException, ApiErrorException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(toJson(body)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 300) {
            ApiErrorDTO error = toObject(response.body(), ApiErrorDTO.class);
            throw new ApiErrorException(error.getMessage());
        }

        return toObject(response.body(), responseClass);
    }

}
