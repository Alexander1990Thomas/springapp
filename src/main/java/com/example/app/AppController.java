package com.example.app;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

@RestController
@RequestMapping("/api")
public class AppController {

    private final List<Map<String, Object>> items = new ArrayList<>();

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        return ResponseEntity.ok(Map.of(
            "app",      "Spring Boot CI/CD Demo",
            "version",  "1.0.0",
            "status",   "running"
        ));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(
            Map.of("status", "healthy")
        );
    }

    @GetMapping("/items")
    public ResponseEntity<List<Map<String, Object>>> getItems() {
        return ResponseEntity.ok(items);
    }

    @PostMapping("/items")
    public ResponseEntity<Map<String, Object>> addItem(
            @RequestBody Map<String, Object> item) {
        item.put("id", items.size() + 1);
        items.add(item);
        return ResponseEntity.status(201).body(item);
    }
}
