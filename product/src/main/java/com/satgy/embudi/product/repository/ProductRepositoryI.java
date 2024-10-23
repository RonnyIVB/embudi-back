package com.satgy.embudi.product.repository;

import com.satgy.embudi.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryI extends JpaRepository<Product, Long> {
}
