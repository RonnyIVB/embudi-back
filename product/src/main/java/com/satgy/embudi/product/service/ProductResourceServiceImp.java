package com.satgy.embudi.product.service;

import com.satgy.embudi.product.general.Str;
import com.satgy.embudi.product.model.File;
import com.satgy.embudi.product.model.ProductResource;
import com.satgy.embudi.product.repository.FileRepositoryI;
import com.satgy.embudi.product.repository.ProductResourceRepositoryI;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductResourceServiceImp implements ProductResourceServiceI {

    @Autowired
    private ProductResourceRepositoryI repo;

    @Autowired
    private FileServiceI serviceFile;

    @Override
    public List<ProductResource> findByProduct(Long productId) {
        return repo.findByProduct(productId);
    }

    @Override
    public Optional<ProductResource> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public ProductResource create(ProductResource productResource) {
        Short order = (short)(1 + repo.findMaxOrderByProduct(productResource.getProduct().getProductId())); // get the last position
        productResource.setOrder(order);
        //if (Str.esNulo(productResource.getName())) productResource.setName(productResource.getFile().getOriginalName());
        return repo.save(productResource);
    }

    @Override
    @Transactional
    public ProductResource update(ProductResource productResource) {
        return repo.save(productResource);
    }

    @Override
    @Transactional
    public void setMain(ProductResource productResource) {
        // only one must be main = true by product
        repo.setAllMainFalseByProduct(productResource.getProduct().getProductId());
        repo.setMainTrueById(productResource.getProductResourceId());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<File> of = serviceFile.findByProductResource(id);
        repo.deleteById(id);
        of.ifPresent(file -> serviceFile.delete(file.getFileId()));
    }


}
