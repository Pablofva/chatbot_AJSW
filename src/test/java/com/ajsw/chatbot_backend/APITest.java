package com.ajsw.chatbot_backend;

import com.ajsw.chatbot_backend.services.AnthropicClient;
import com.ajsw.chatbot_backend.services.SentimentAnalysisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class APITest {

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AnthropicClient anthropicClient;

    @Test
    public void testSentimentAnalysisService() {
        String testText = "Estoy muy feliz de estar aprendiendo a programar.";
        float sentimentScore = 0;
        try {
            sentimentScore = sentimentAnalysisService.analyzeSentiment(testText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Sentiment Score: " + sentimentScore);
        assertTrue(sentimentScore >= -1.0 && sentimentScore <= 1.0, "Sentiment score should be between -1.0 and 1.0");
    }

    @Test
    public void testAnthropicClient() {
        String prompt = "¿Qué opinas de la programación en Java?";
        String response = null;
        try {
            response = anthropicClient.complete(prompt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Anthropic Response: " + response);
        assertNotNull(response, "Response should not be null");
    }
}
