package com.ajsw.chatbot_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PrefectChatbot implements Chatbot {

    @Value("${prefect.name}")
    private String name;

    @Value("${prefect.house}")
    private String house;

    private final AnthropicClient anthropicClient;

    @Autowired
    public PrefectChatbot(AnthropicClient anthropicClient) {
        this.anthropicClient = anthropicClient;
    }

    public String getName() {
        return name;
    }

    public String getHouse() {
        return house;
    }

    public String processInput(String userInput) {
        String prompt = generatePrompt(userInput);
        return anthropicClient.complete(prompt);
    }

    private String generatePrompt(String userInput) {
        return String.format("""
            Eres %s, prefecto/a de %s. %s
            Responde al siguiente mensaje de un estudiante de %s de manera acorde a tu personalidad:
            
            Estudiante: %s
            %s:
            """, name, house, getPersonalityTrait(), house, userInput, name);
    }

    public String getWelcomeMessage() {
        return String.format("¡Bienvenido a %s! Soy %s, tu prefecto/a. %s ¿En qué puedo ayudarte?",
                house, name, getHouseValue());
    }

    private String getPersonalityTrait() {
        switch (house) {
            case "Gryffindor":
                return "Eres valiente, leal y siempre defiendes lo que es correcto.";
            case "Hufflepuff":
                return "Eres leal, paciente y trabajador.";
            case "Ravenclaw":
                return "Eres inteligente, creativa y siempre buscas el conocimiento.";
            case "Slytherin":
                return "Eres astuto, ambicioso y determinado.";
            default:
                return "";
        }
    }

    private String getHouseValue() {
        switch (house) {
            case "Gryffindor":
                return "Aquí valoramos la valentía y la nobleza.";
            case "Hufflepuff":
                return "Aquí valoramos la lealtad y el trabajo duro.";
            case "Ravenclaw":
                return "Aquí valoramos la sabiduría y el ingenio.";
            case "Slytherin":
                return "Aquí valoramos la astucia y la ambición.";
            default:
                return "";
        }
    }
}
