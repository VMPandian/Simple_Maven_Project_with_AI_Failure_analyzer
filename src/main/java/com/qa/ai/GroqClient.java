package com.qa.ai;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;

public class GroqClient {

    private final String GROQ_URL;

    private final String MODEL;

    private final String apiKey;

    private final HttpClient httpClient;

    public GroqClient() {

        Dotenv dotenv = Dotenv.load();

        apiKey = dotenv.get("GROQ_API_KEY");
        GROQ_URL = dotenv.get("GROQ_URL");
        MODEL = dotenv.get("GROQ_MODEL");
        
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "GROQ_API_KEY is missing from .env"
            );
        }

        httpClient = HttpClient.newHttpClient();
    }

    public String sendRequest(String requestBody) throws Exception {

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(GROQ_URL))
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

    HttpResponse<String> response =
            httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

    if (response.statusCode() < 200 || response.statusCode() >= 300) {
        throw new RuntimeException(
                "Groq API failed. HTTP Status: "
                        + response.statusCode()
                        + "\nResponse: "
                        + response.body()
        );
    }

    ObjectMapper objectMapper = new ObjectMapper();

    JsonNode root = objectMapper.readTree(response.body());

    return root
            .path("choices")
            .path(0)
            .path("message")
            .path("content")
            .asText();
        }
}