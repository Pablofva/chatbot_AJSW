package com.ajsw.chatbot_backend.dto;

import com.ajsw.chatbot_backend.models.enums.HogwartsHouse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String email;
    private String username;
    private String password;
    private String gender;
}