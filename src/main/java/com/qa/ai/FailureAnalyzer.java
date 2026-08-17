package com.qa.ai;

import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FailureAnalyzer {

    private final GroqClient groqClient;
    private final String skill;

    public FailureAnalyzer() throws Exception {

        skill = Files.readString(
                Path.of("skill.md")
        );

        groqClient = new GroqClient();
    }

    public FailureAnalysis analyze(String failureMessage) throws Exception {

        String requestBody = """
                {
                  "model": "openai/gpt-oss-20b",
                  "messages": [
                    {
                      "role": "system",
                      "content": %s
                    },
                    {
                      "role": "user",
                      "content": %s
                    }
                  ]
                }
                """.formatted(
                quoteJson(skill),
                quoteJson(failureMessage)
        );

        // Send failure message to Groq
        String aiResponse = groqClient.sendRequest(requestBody);

        // Display raw AI response for debugging
        System.out.println("\n===== RAW AI RESPONSE =====");
        System.out.println(aiResponse);
        System.out.println("===========================\n");

        // Convert JSON response into FailureAnalysis object
        ObjectMapper objectMapper = new ObjectMapper();

        FailureAnalysis analysis = objectMapper.readValue(
                aiResponse,
                FailureAnalysis.class
        );

        // Validate Failure Category
        if (analysis.getFailureCategory() == null
                || analysis.getFailureCategory().isBlank()) {

            throw new IllegalStateException(
                    "AI response does not contain a valid failureCategory"
            );
        }

        // Validate Root Causes
        if (analysis.getPossibleRootCauses() == null
                || analysis.getPossibleRootCauses().size() != 3) {

            throw new IllegalStateException(
                    "AI response must contain exactly 3 possible root causes"
            );
        }

        // Validate Solutions
        if (analysis.getSolutions() == null
                || analysis.getSolutions().size() != 3) {

            throw new IllegalStateException(
                    "AI response must contain exactly 3 solutions"
            );
        }

        return analysis;
    }

    private String quoteJson(String value) {

        return "\"" + value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n")
                .replace("\t", "\\t")
                + "\"";
    }
}