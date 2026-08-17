package com.virtualresearch.virtualmarketresearchbackend.controller;

import com.virtualresearch.virtualmarketresearchbackend.model.Product;
import com.virtualresearch.virtualmarketresearchbackend.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
@RestController
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {

        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found");
        }

        Product product = productRepository.findById(id).get();

        return ResponseEntity.ok(product);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productRepository.save(product));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid Product product) {

        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found");
        }
        product.setId(id);
        return ResponseEntity.ok(productRepository.save(product));

    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {

        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found");
        }

        productRepository.deleteById(id);

        return ResponseEntity.ok("Product deleted successfully");
    }
}