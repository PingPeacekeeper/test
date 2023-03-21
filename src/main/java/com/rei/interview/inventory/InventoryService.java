package com.rei.interview.inventory;

import com.rei.interview.location.LocationService;
import com.rei.interview.product.Product;
import com.rei.interview.rs.Location;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Component
public class InventoryService {

    private LocationService locationService;

    private final InventoryRepository repository;

    @Autowired
    public InventoryService(LocationService locationService, InventoryRepository inventoryRepository) {
        this.locationService = locationService;
        this.repository = inventoryRepository;
    }

    @PostConstruct
    public void readInInventory() throws IOException {
        try (Reader in = new InputStreamReader(getClass().getResourceAsStream("/product_inventory.csv"))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("productId", "location", "quantity")
                    .withFirstRecordAsHeader()
                    .parse(in);

            for (CSVRecord record : records) {
                String productId = record.get("productId");
                String location = record.get("location");
                int quantity = Integer.valueOf(record.get("quantity"));
                System.out.println(productId + "\t" + location + "\t" + quantity);

                Inventory inventory = new Inventory(productId, Location.valueOf(location), quantity);
                repository.put(inventory);
            }
        }
    }

    public boolean hasInventoryOnline(Product product, int quantity) {
        int cnt = 0;
        for (Location location : Location.values()) {
            Inventory inventory = repository.get(product.getProductId(),location);
            if (inventory == null) continue;
            cnt += inventory.getQuantity();
        }
        return cnt >= quantity;
    }

    public boolean hasInventoryInNearbyStores(Product product, int quantity, Location currentLocation) {
        Inventory inventory = repository.get(product.getProductId(), currentLocation);
        if (inventory == null) return false;
        return inventory.getQuantity() >= quantity;
    }
}
