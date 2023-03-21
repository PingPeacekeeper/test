package com.rei.interview.product;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void isValidProduct(Product product) throws Exception {
        if (product.getProductId() == null || product.getProductId().isEmpty()) {
            throw new Exception("empty product id");
        }
        if (product.getPrice() == null || product.getPrice().equals(BigDecimal.ZERO)) {
            throw new Exception("empty product price");
        }
        if (product.getBrand() == null || product.getBrand().isEmpty()) {
            throw new Exception("empty brand");
        }
    }

    public Product getProduct(String productId) {
        return productRepository.getProductById(productId);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productRepository.getAll());
    }

    public List<Product> getProductsByBrand(String brand) {
        Set<String> productIdByBrand = productRepository.getProductIdByBrand(brand);
        if (productIdByBrand == null || productIdByBrand.size() == 0) return null;
        List<Product> list = productIdByBrand.stream()
                .map((id) -> productRepository.getProductById(id))
                .collect(Collectors.toList());
        return list;
    }

    public Product addNewProduct(Product product) throws Exception {
        isValidProduct(product);
        return productRepository.addProduct(product);
    }



    /**
     * Populates the product repository with data from products.txt
     *
     * @throws IOException
     */
    @PostConstruct
    public void populateProducts() throws IOException {
        try (Reader in = new InputStreamReader(getClass().getResourceAsStream("/products.csv"))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("productId", "brand", "description", "price")
                    .withFirstRecordAsHeader()
                    .parse(in);

            for (CSVRecord record : records) {
                Product product = new Product();
                product.setProductId(record.get("productId"));
                product.setBrand(record.get("brand"));
                product.setDescription(record.get("description"));
                product.setPrice(new BigDecimal(record.get("price")));
                logger.info(product.toString());
                productRepository.addProduct(product);
            }
        }

        logger.info("Products loaded into product repository");
    }

}
