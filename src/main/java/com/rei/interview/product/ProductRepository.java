package com.rei.interview.product;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProductRepository {

    private final Map<String, Product> products = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> brandList= new ConcurrentHashMap<>();

    public Product addProduct(Product product) {
        products.put(product.getProductId(), product);
        Set<String> set = brandList.computeIfAbsent(product.getBrand(), k -> new HashSet<>());
        if(set.add(product.getProductId())) return product;
        return null;
    }

    public Product getProductById(String productId) {
        return products.get(productId);
    }

    public Set<String> getProductIdByBrand(String brand){
        return brandList.get(brand);
    }

    public Collection<Product> getAll() {
        return products.values();
    }
}
