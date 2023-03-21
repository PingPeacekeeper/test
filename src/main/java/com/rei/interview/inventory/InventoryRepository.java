package com.rei.interview.inventory;

import com.rei.interview.rs.Location;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InventoryRepository {

    private final Map<String, Inventory> map;

    public InventoryRepository() {
        this.map = new ConcurrentHashMap<>();
    }

    public void put(Inventory inventory) {
        map.put(inventory.getInventoryKey(), inventory);
    }

    public Inventory get(Inventory inventory) {
        return map.get(inventory.getInventoryKey());
    }

    public Inventory get(String productId, Location location) {
        return map.get(Inventory.generateKey(productId, location));
    }
}
