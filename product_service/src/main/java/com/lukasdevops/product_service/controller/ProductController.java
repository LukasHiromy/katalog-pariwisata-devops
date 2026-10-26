package com.lukasdevops.product_service.controller;

import com.lukasdevops.product_service.entity.Product;
import com.lukasdevops.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ==========================================
    // GET /api/status
    // ==========================================
    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> getStatus() {
        // Mengembalikan respons JSON sederhana untuk pengujian uptime
        return ResponseEntity.ok(Map.of("status", "UP", "message", "Backend Service berjalan normal"));
    }

    // ==========================================
    // GET /api/products
    // ==========================================
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // ==========================================
    // GET /api/products/{id}
    // ==========================================
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // ==========================================
    // POST /api/products
    // ==========================================
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
}