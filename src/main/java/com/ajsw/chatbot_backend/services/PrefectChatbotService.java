package com.ajsw.chatbot_backend.services;

import com.ajsw.chatbot_backend.models.Conversation;
import com.ajsw.chatbot_backend.models.enums.ConversationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrefectChatbotService {

    private final Map<String, PrefectChatbot> prefectChatbots; // Mapa de prefectos
    private final ChatbotService chatbotService; // Inyección del servicio común

    @Autowired
    public PrefectChatbotService(Map<String, PrefectChatbot> prefectChatbots, ChatbotService chatbotService) {
        this.prefectChatbots = prefectChatbots;
        this.chatbotService = chatbotService;
    }

    public String getResponse(String house, String userInput) {
        PrefectChatbot prefect = prefectChatbots.get(house);
        if (prefect == null) {
            return "Error: No se encontró un prefecto para la casa " + house;
        }

        String response = prefect.processInput(userInput);

        // Guardar la conversación utilizando ChatbotService
        Conversation conversation = new Conversation(); // ... (crear y llenar el objeto Conversation)
        conversation.setType(ConversationType.PREFECT);
        chatbotService.saveConversation(conversation);

        return response;
    }
}
