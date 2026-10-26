package com.lukasdevops.product_service.repository;

import com.lukasdevops.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // JpaRepository secara otomatis menyediakan metode bawaan seperti:
    // - findAll() untuk mengambil semua produk
    // - findById(Long id) untuk mengambil spesifik produk
    // - save(Product entity) untuk menyimpan data produk baru
}