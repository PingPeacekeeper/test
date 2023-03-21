package com.rei.interview.inventory;


import com.rei.interview.rs.Location;

public class Inventory {

    private String productId;
    private String inventoryKey;
    private Location location;
    private int quantity;


    public Inventory(String productId, Location location, int quantity) {
        this.inventoryKey = Inventory.generateKey(productId, location);
        this.productId = productId;
        this.location = location;
        this.quantity = quantity;
    }

    public static String generateKey(String productId, Location location) {
        return productId + ":" + location;
    }

    public String getInventoryKey() {
        return inventoryKey;
    }

    public void setInventoryKey(String inventoryKey) {
        this.inventoryKey = inventoryKey;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
