package com.ajsw.chatbot_backend.services;

import com.ajsw.chatbot_backend.models.enums.HogwartsHouse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HatChatbot implements Chatbot{

    private final SentimentAnalysisService sentimentService;
    private final AnthropicClient anthropicClient;
    @Value("${claude.api.key}")
    private String apiKey;

    @Value("${claude.api.url}")
    private String apiUrl;
@Getter
    private final Map<String, List<String>> questions = new HashMap<>() {{
        put("Cualidad", List.of("Valentía", "Inteligencia", "Lealtad", "Ambición"));
        put("Mayor miedo", List.of("La oscuridad", "El fracaso", "La soledad", "El rechazo"));
        put("Poder Magico favorito", List.of("Volar", "Leer mentes", "Hacer invisibles a otros", "Transformar objetos"));
        put("Criatura magica favorita", List.of("Dragón", "Fénix", "Hipogrifo", "Thestral"));
        put("Intereses de Hogwarts", List.of("Su historia y tradiciones", "Sus clases y conocimientos", "Sus amistades y compañerismo", "Sus oportunidades y desafíos"));
        put("Asignatura favorita", List.of("Defensa Contra las Artes Oscuras", "Transformaciones", "Encantamientos", "Pociones"));
        put("Cualidades admiradas en otros", List.of("El coraje", "La sabiduría", "La bondad", "La astucia"));
    }};
    @Autowired
    public HatChatbot(SentimentAnalysisService sentimentService, AnthropicClient anthropicClient) {
        this.sentimentService = sentimentService;
        this.anthropicClient = anthropicClient;
    }

    public String processInput(String userInput) {
        try {
            float sentimentScore = sentimentService.analyzeSentiment(userInput);
            String sentiment = sentimentScore > 0 ? "positive" : (sentimentScore < 0 ? "negative" : "neutral");

            String prompt = String.format("User input: %s", userInput);
            String response = anthropicClient.complete(prompt);

            return String.format("Sentiment: %s\nResponse: %s", sentiment, response);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error analyzing sentiment: " + e.getMessage();
        }
    }

    public HogwartsHouse assignHouse(Map<String, String>interests) {
        // Implementa tu lógica de análisis aquí
        // Aquí simplemente retornamos una casa aleatoria como ejemplo
        HogwartsHouse[] houses = HogwartsHouse.values();
        //int randomIndex = (int) (Math.random() * houses.length);
        return houses[1];
    }
}
