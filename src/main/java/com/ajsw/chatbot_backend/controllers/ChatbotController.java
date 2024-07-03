package com.ajsw.chatbot_backend.controllers;

import com.ajsw.chatbot_backend.models.ChatbotResponse;
import com.ajsw.chatbot_backend.models.User;
import com.ajsw.chatbot_backend.models.enums.HogwartsHouse;
import com.ajsw.chatbot_backend.services.HatChatbot;
import com.ajsw.chatbot_backend.services.PrefectChatbotService;
import com.ajsw.chatbot_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @PostMapping("/hat-selection")
    public ChatbotResponse processHatSelection(
            @RequestBody Map<String, Object> requestBody
    ) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String username;
            if (principal instanceof UserDetails) {

                username = ((UserDetails)principal).getUsername();
            } else {
                username = principal.toString();
            }

            Map<String, String> answers = (Map<String, String>) requestBody.get("answers");

            // Lógica del Sombrero Seleccionador
            HogwartsHouse assignedHouse = hatChatbot.assignHouse(answers);
            System.out.println("Assigned house: " + assignedHouse);
            String response = String.format("Bienvenido a %s!", assignedHouse);

            // Actualizar la casa del usuario en la base de datos
            User user = userService.findByUsername(username);
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
