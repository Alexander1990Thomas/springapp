package com.example.app;

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {

    private ItemService service;

    @BeforeEach
    void setUp() {
        service = new ItemService();
    }

    @Test
    void testGetAllItemsEmpty() {
        assertTrue(service.getAllItems().isEmpty());
    }

    @Test
    void testAddItem() {
        Map<String, Object> item = new HashMap<>();
        item.put("name", "test item");

        Map<String, Object> result = 
            service.addItem(item);

        assertEquals(1, result.get("id"));
        assertEquals("test item", result.get("name"));
        assertNotNull(result.get("createdAt"));
    }

    @Test
    void testAddItemEmptyName() {
        Map<String, Object> item = new HashMap<>();
        item.put("name", "");

        assertThrows(
            IllegalArgumentException.class,
            () -> service.addItem(item)
        );
    }

    @Test
    void testAddItemNullName() {
        Map<String, Object> item = new HashMap<>();

        assertThrows(
            IllegalArgumentException.class,
            () -> service.addItem(item)
        );
    }

    @Test
    void testGetItem() {
        Map<String, Object> item = new HashMap<>();
        item.put("name", "find me");
        service.addItem(item);

        Optional<Map<String,Object>> found = 
            service.getItem(1);

        assertTrue(found.isPresent());
        assertEquals("find me", 
            found.get().get("name"));
    }

    @Test
    void testGetItemNotFound() {
        Optional<Map<String,Object>> found = 
            service.getItem(999);
        assertFalse(found.isPresent());
    }

    @Test
    void testDeleteItem() {
        Map<String, Object> item = new HashMap<>();
        item.put("name", "delete me");
        service.addItem(item);

        assertTrue(service.deleteItem(1));
        assertTrue(service.getAllItems().isEmpty());
    }

    @Test
    void testDeleteItemNotFound() {
        assertFalse(service.deleteItem(999));
    }

    @Test
    void testGetCount() {
        assertEquals(0, service.getCount());

        Map<String, Object> item = new HashMap<>();
        item.put("name", "item 1");
        service.addItem(item);

        assertEquals(1, service.getCount());
    }

    @Test
    void testMultipleItems() {
        for (int i = 1; i <= 5; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", "item " + i);
            service.addItem(item);
        }

        assertEquals(5, service.getCount());
        assertEquals(5, 
            service.getAllItems().size());
    }
}

