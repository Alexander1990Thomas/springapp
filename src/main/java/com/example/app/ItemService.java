package com.example.app;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ItemService {

    private final List<Map<String,Object>> items 
        = new ArrayList<>();

    public List<Map<String,Object>> getAllItems() {
        return Collections.unmodifiableList(items);
    }

    public Map<String, Object> addItem(
            Map<String, Object> item) {
        if (item.get("name") == null || 
            item.get("name").toString().isBlank()) {
            throw new IllegalArgumentException(
                "Item name cannot be empty"
            );
        }
        item.put("id", items.size() + 1);
        item.put("createdAt", 
            System.currentTimeMillis());
        items.add(item);
        return item;
    }

    public Optional<Map<String,Object>> getItem(
            int id) {
        return items.stream()
            .filter(i -> i.get("id").equals(id))
            .findFirst();
    }

    public boolean deleteItem(int id) {
        return items.removeIf(
            i -> i.get("id").equals(id)
        );
    }

    public int getCount() {
        return items.size();
    }
}
