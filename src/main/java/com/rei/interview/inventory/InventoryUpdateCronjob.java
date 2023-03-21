package com.rei.interview.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class InventoryUpdateCronjob {
    private final InventoryService service;

    @Autowired
    public InventoryUpdateCronjob(InventoryService service) {
        this.service = service;
    }


    @Scheduled(fixedRate = 100000)
    public void update() throws IOException {
        // r/w to update
        service.readInInventory();
    }
}
