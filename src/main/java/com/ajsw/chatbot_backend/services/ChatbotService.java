package com.ajsw.chatbot_backend.services;

import com.ajsw.chatbot_backend.models.Conversation;
import com.ajsw.chatbot_backend.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatbotService {

    private final ConversationRepository conversationRepository;

    @Autowired
    public ChatbotService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    // Lógica común para guardar conversaciones
    public void saveConversation(Conversation conversation) {
        conversation.setStartTime(LocalDateTime.now());
        conversation.setEndTime(LocalDateTime.now());
        conversationRepository.save(conversation);
    }

    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }
}
