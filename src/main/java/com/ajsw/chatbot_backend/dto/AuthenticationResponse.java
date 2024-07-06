package com.ajsw.chatbot_backend.dto;

import com.ajsw.chatbot_backend.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {
    private User user;
    private String token;
}