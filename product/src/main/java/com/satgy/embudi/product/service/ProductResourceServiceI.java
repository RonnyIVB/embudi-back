package com.satgy.embudi.product.service;

import com.satgy.embudi.product.model.ProductResource;

import java.util.List;
import java.util.Optional;

public interface ProductResourceServiceI {
    public List<ProductResource> findByProduct(Long productId);
    public Optional<ProductResource> findById(Long id);
    public ProductResource create(ProductResource productResource);
    public ProductResource update(ProductResource productResource);
    public void setMain(ProductResource productResource);
    public void delete(Long id);
}
