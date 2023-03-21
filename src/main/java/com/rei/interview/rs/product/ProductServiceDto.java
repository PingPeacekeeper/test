package com.rei.interview.rs.product;

import com.rei.interview.product.Product;

import java.math.BigDecimal;

public class ProductServiceDto {

    private String productId;
    private String brand;
    private BigDecimal price;

    public ProductServiceDto(String productId, String brand, BigDecimal price) {
        this.productId = productId;
        this.brand = brand;
        this.price = price;
    }

    public ProductServiceDto(Product product) {
        this.productId = product.getProductId();
        this.brand = product.getBrand();
        this.price = product.getPrice();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
