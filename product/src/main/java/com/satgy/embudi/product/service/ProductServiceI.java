package com.satgy.embudi.product.service;

import com.satgy.embudi.product.dto.ProductAllData;
import com.satgy.embudi.product.general.CustomFilter;
import com.satgy.embudi.product.general.CustomResponse;
import com.satgy.embudi.product.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductServiceI {
    public Optional<Product> findById(Long id);
    public Optional<ProductAllData> getAllDataById(Long productId);
    public CustomResponse customSearch(int pageNumber, int pageSize, String order, String uuid, List<CustomFilter> filters);
    public Product create(Product product);
    public Product update(Product product);
    public void delete(Long id);
}
