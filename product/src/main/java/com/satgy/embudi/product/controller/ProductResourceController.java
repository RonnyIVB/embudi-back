package com.satgy.embudi.product.controller;

import com.satgy.embudi.product.general.Fun;
import com.satgy.embudi.product.model.ProductResource;
import com.satgy.embudi.product.model.ProductResource;
import com.satgy.embudi.product.service.ProductResourceServiceI;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/product/resource")
@CrossOrigin("*")
public class ProductResourceController {

    @Autowired
    private ProductResourceServiceI service;
    
    @GetMapping("/byproduct/{id}")
    public ResponseEntity<List<ProductResource>> findByProductId(@PathVariable("id") Long productId){
        return ResponseEntity.ok(service.findByProduct(productId));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductResource productResource) {
        ProductResource newPR;
        try {
            newPR = service.create(productResource);
            return new ResponseEntity<>(newPR, HttpStatus.CREATED);
        } catch (FeignException.FeignClientException fe) {
            return Fun.getResponse("No existe el usuario", "No existe el usuario. " + fe.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/setMain/{id}")
    public ResponseEntity<Boolean> setMain(@PathVariable("id") Long id) {
        Optional<ProductResource> optionalProductResource = service.findById(id);
        return optionalProductResource.map(productResource -> {
                service.setMain(productResource);
                return ResponseEntity.ok(true);
            }).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<ProductResource> update(@Valid @RequestBody ProductResource productResource) {
        Optional<ProductResource> optionalProductResource = service.findById(productResource.getProductResourceId());
        return optionalProductResource.map(pr ->
                    ResponseEntity.ok(service.update(productResource)))
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResource> delete(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(i -> {
                    service.delete(i.getProductResourceId());
                    return ResponseEntity.ok(i);
                })
                .orElseGet(()->ResponseEntity.notFound().build());
    }
}
