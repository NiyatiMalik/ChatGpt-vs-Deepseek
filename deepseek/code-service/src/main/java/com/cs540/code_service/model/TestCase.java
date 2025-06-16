package com.cs540.code_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Data
@Document(collection = "test_cases")
public class TestCase {
    @Id
    private String id;
    private String problemId;
    private Object input;
    private Object expectedOutput;
    private int timeoutSeconds = 5;
    private boolean hidden;

    private static final ObjectMapper mapper = new ObjectMapper();

    public String getInputAsString() {
        if (input instanceof String) {
            return (String) input;
        }
        try {
            return mapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize input", e);
        }
    }
}