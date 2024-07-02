package com.ajsw.chatbot_backend.models;


import com.ajsw.chatbot_backend.models.enums.ConversationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private ConversationType type;

    @ElementCollection
    @CollectionTable(name = "conversation_messages", joinColumns = @JoinColumn(name = "conversation_id"))
    @OrderColumn(name = "message_order")
    private List<String> messages = new ArrayList<>();

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
