package com.ajsw.chatbot_backend.controllers;

import com.ajsw.chatbot_backend.models.ChatbotResponse;
import com.ajsw.chatbot_backend.models.User;
import com.ajsw.chatbot_backend.models.enums.HogwartsHouse;
import com.ajsw.chatbot_backend.services.HatChatbot;
import com.ajsw.chatbot_backend.services.PrefectChatbotService;
import com.ajsw.chatbot_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    @Autowired
    private UserService userService;

    private final HatChatbot hatChatbot;
    private final PrefectChatbotService prefectChatbotService;

    @Autowired
    public ChatbotController(HatChatbot hatChatbot, PrefectChatbotService prefectChatbotService) {
        this.hatChatbot = hatChatbot;
        this.prefectChatbotService = prefectChatbotService;
    }

    @GetMapping("/questions")
    public Map<String, List<String>> getQuestions() {
        return hatChatbot.getQuestions();
    }

    /**
     * Process the hat selection for a user based on their answers to the hat selection questions.
     *
     * @param requestBody The request body containing the userId and answers to the questions
     * @return A ChatbotResponse object with the assigned house and a welcome message, or an error message if the user is not found or an error occurs
     */
    @PostMapping("/hat-selection")
    public ChatbotResponse processHatSelection(
            @RequestBody Map<String, Object> requestBody
    ) {
        try {
            long userId = Long.parseLong(requestBody.get("userId").toString());
            Map<String, String> answers = (Map<String, String>) requestBody.get("answers");

            // Lógica del Sombrero Seleccionador
            HogwartsHouse assignedHouse = hatChatbot.assignHouse(answers);
            System.out.println("Assigned house: " + assignedHouse);
            String response = String.format("Bienvenido a %s!", assignedHouse);

            // Actualizar la casa del usuario en la base de datos
            User user = userService.getUserById(userId);
            if (user != null) {
                user.setHouse(assignedHouse);
                userService.updateUser(user);
            } else {
                // Manejar el caso en que el usuario no se encuentre
                return new ChatbotResponse("Error: Usuario no encontrado.", null);
            }

            return new ChatbotResponse(response, assignedHouse.name());

        } catch (Exception e) {
            e.printStackTrace();
            return new ChatbotResponse("Error", "Error al obtener respuesta del bot");
        }
    }
}
