package com.aiinterview.profile_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
public class HealthCheckController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/health")
    public String healthCheck() {
        try (Connection conn = dataSource.getConnection()) {
            return "✅ Connected to MySQL!";
        } catch (SQLException e) {
            return "❌ Failed to connect to MySQL: " + e.getMessage();
        }
    }
}
