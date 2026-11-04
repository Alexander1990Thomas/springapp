package com.example.app;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
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
                .value("running"))
            .andExpect(jsonPath("$.items")
                .exists());
    }

    @Test
    void testHealth() throws Exception {
        mockMvc.perform(get("/api/health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status")
                .value("healthy"));
    }

    @Test
    void testGetItemsEmpty() throws Exception {
        mockMvc.perform(get("/api/items"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testAddItem() throws Exception {
        mockMvc.perform(post("/api/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"test item\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name")
                .value("test item"));
    }

    @Test
    void testAddItemEmptyName() throws Exception {
        mockMvc.perform(post("/api/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error")
                .exists());
    }

    @Test
    void testGetItemNotFound() throws Exception {
        mockMvc.perform(get("/api/items/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteItemNotFound() throws Exception {
        mockMvc.perform(delete("/api/items/999"))
            .andExpect(status().isNotFound());
    }
}
