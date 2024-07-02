package com.ajsw.chatbot_backend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnthropicClient {

    @Value("${claude.api.key}")
    private String apiKey;

    @Value("${claude.api.url}")
    private String apiUrl;

    public String complete(String prompt) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        headers.set("anthropic-version", "2023-06-01");
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "claude-3-5-sonnet-20240620");
        requestBody.put("max_tokens", 1024);
        requestBody.put("messages", Collections.singletonList(Map.of(
                "role", "user",
                "content", prompt
        )));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return parseAnthropicResponse(response.getBody());
        } else {
            throw new RuntimeException("Error: Unable to get response from Claude");
        }
    }
    public String generateInterestQuestions(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        headers.set("anthropic-version", "2023-06-01");
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "claude-3-5-sonnet-20240620");
        requestBody.put("max_tokens", 1024);
        requestBody.put("messages", Collections.singletonList(Map.of(
                "role", "user",
                "content", "Generate questions to learn about the user's interests."
        )));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return parseAnthropicResponse(response.getBody());
        } else {
            throw new RuntimeException("Error: Unable to get response from Claude");
        }
    }
    private String parseAnthropicResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            return rootNode.path("content").path(0).path("text").asText();
        } catch (Exception e) {
            throw new RuntimeException("Error parsing Anthropic response", e);
        }
    }
}
