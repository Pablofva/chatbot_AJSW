package com.ajsw.chatbot_backend.repository;

import com.ajsw.chatbot_backend.models.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}