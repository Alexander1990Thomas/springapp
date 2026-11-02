package com.example.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHome() throws Exception {
        mockMvc.perform(get("/api/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.app").exists())
            .andExpect(jsonPath("$.status")
                .value("running"));
    }

    @Test
    void testHealth() throws Exception {
        mockMvc.perform(get("/api/health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status")
                .value("healthy"));
    }

    @Test
    void testGetItems() throws Exception {
        mockMvc.perform(get("/api/items"))
            .andExpect(status().isOk());
    }

    @Test
    void testAddItem() throws Exception {
        mockMvc.perform(post("/api/items")
            .contentType("application/json")
            .content("{\"name\":\"Test item\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name")
                .value("Test item"));
    }
}
