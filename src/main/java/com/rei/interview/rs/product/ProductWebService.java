package com.rei.interview.rs.product;

import com.rei.interview.product.Product;
import com.rei.interview.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductWebService {

    private final ProductService service;

    @Autowired
    public ProductWebService(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductServiceDto> getProductById(@PathVariable String id) {
        Product product = service.getProduct(id);
        if (product == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ProductServiceDto(product), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductServiceDto>> getProductsByBrand(@RequestParam String brand) {
        List<Product> list = service.getProductsByBrand(brand);
        if (list == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        List<ProductServiceDto> res = list.stream().map(ProductServiceDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) throws Exception {
        Product product1 = service.addNewProduct(product);
        if (product1 == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(product1, HttpStatus.OK);
    }


}
