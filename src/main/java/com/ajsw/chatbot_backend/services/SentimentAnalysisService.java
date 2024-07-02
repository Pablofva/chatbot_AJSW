package com.ajsw.chatbot_backend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class SentimentAnalysisService {

    @Value("${google.cloud.api.key}")
    private String apiKey;

    @Value("${google.cloud.project-id}")
    private String projectId;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SentimentAnalysisService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public float analyzeSentiment(String text) throws IOException {
        String url = String.format("https://language.googleapis.com/v1/documents:analyzeSentiment?key=%s", apiKey);

        Map<String, Object> document = new HashMap<>();
        document.put("type", "PLAIN_TEXT");
        document.put("content", text);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("document", document);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode root = objectMapper.readTree(response.getBody());
                return root.path("documentSentiment").path("score").floatValue();
            } else {
                throw new IOException("Failed to analyze sentiment: " + response.getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error connecting to Google Cloud API", e);
        }
    }
}
