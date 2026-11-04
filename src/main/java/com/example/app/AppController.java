package com.example.app;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public ResponseEntity<Map<String,Object>> home() {
        return ResponseEntity.ok(Map.of(
            "app",     "Spring Boot CI/CD Demo",
            "version", "1.0.0",
            "status",  "running",
            "items",   itemService.getCount()
        ));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String,String>> health() {
        return ResponseEntity.ok(
            Map.of("status", "healthy")
        );
    }

    @GetMapping("/items")
    public ResponseEntity<List<Map<String,Object>>> getItems() {
        return ResponseEntity.ok(
            itemService.getAllItems()
        );
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<?> getItem(
            @PathVariable int id) {
        return itemService.getItem(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/items")
    public ResponseEntity<?> addItem(
            @RequestBody Map<String, Object> item) {
        try {
            Map<String, Object> created = 
                itemService.addItem(item);
            return ResponseEntity
                .status(201)
                .body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                .badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Map<String,Object>> deleteItem(@PathVariable int id) {
        boolean deleted = itemService.deleteItem(id);
        if (deleted) {
            return ResponseEntity.ok(
                Map.of("deleted", id)
            );
        }
        return ResponseEntity.notFound().build();
    }
}
