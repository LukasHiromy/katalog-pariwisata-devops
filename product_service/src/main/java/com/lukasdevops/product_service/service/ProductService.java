package com.lukasdevops.product_service.service;

import com.lukasdevops.product_service.entity.Product;
import com.lukasdevops.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public ProductService(ProductRepository productRepository, RedisTemplate<String, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }

    // ==========================================
    // GET /api/products
    // ==========================================
    public List<Product> getAllProducts() {
        String cacheKey = "products:all";

        // 1. Cek Redis (Memprioritaskan pengecekan ke redisTemplate)
        List<Product> cachedProducts = (List<Product>) redisTemplate.opsForValue().get(cacheKey);

        if (cachedProducts != null) {
            // Cache Hit: Langsung mengembalikan respons
            return cachedProducts;
        }

        // 2. Cache Miss: Kueri ke database utama (PostgreSQL)
        List<Product> products = productRepository.findAll();

        // 3. Simpan ke Redis dengan Time To Live (TTL) 1 jam
        redisTemplate.opsForValue().set(cacheKey, products, Duration.ofHours(1));

        return products;
    }

    // ==========================================
    // GET /api/products/{id}
    // ==========================================
    public Product getProductById(Long id) {
        // Membuat kunci cache spesifik, misal: product:123
        String cacheKey = "product:" + id;

        // 1. Cek Redis
        Product cachedProduct = (Product) redisTemplate.opsForValue().get(cacheKey);

        if (cachedProduct != null) {
            // Cache Hit
            return cachedProduct;
        }

        // 2. Cache Miss: Ambil dari DB
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produk dengan ID " + id + " tidak ditemukan"));

        // 3. Simpan ke Redis dengan TTL 1 Jam
        redisTemplate.opsForValue().set(cacheKey, product, Duration.ofHours(1));

        return product;
    }

    // ==========================================
    // POST /api/products
    // ==========================================
    public Product createProduct(Product product) {
        // Simpan data baru ke PostgreSQL
        Product savedProduct = productRepository.save(product);

        // Logika Eviction: Otomatis menghapus cache lama karena data telah berubah
        redisTemplate.delete("products:all");

        return savedProduct;
    }
}