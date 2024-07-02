package com.ajsw.chatbot_backend.models;

import com.ajsw.chatbot_backend.models.enums.HogwartsHouse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String username;
    @JsonIgnore
    private String password; // Nuevo campo para la contraseña
    private String gender;

    @Enumerated(EnumType.STRING)
    private HogwartsHouse house;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversation> conversations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserInteraction> contentInteractions = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "user_interests", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "interest")
    private List<String> interests = new ArrayList<>();
}