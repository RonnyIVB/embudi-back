package com.satgy.embudi.product.controller;

import com.satgy.embudi.product.dto.ProductAllData;
import com.satgy.embudi.product.general.CustomFilter;
import com.satgy.embudi.product.general.CustomResponse;
import com.satgy.embudi.product.general.Fun;
import com.satgy.embudi.product.model.Product;
import com.satgy.embudi.product.service.ProductServiceI;
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
@RequestMapping("/api/product")
@CrossOrigin("*")
public class ProductController {
    
    @Autowired
    private ProductServiceI service;

    @RequestMapping("/custom") // post
    public ResponseEntity<CustomResponse> custom(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "12") Integer pageSize,
            @RequestParam(defaultValue = "code") String order,
            @RequestParam(defaultValue = "") String uuid,
            @RequestBody List<CustomFilter> filters
    ) {
        return ResponseEntity.ok(service.customSearch(pageNumber, pageSize, order, uuid, filters));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Long productId){
        return (ResponseEntity<Product>) service.findById(productId)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/alldata/{id}")
    public ResponseEntity<ProductAllData> getAllDataById(@PathVariable("id") Long productId){
        return service.getAllDataById(productId)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product) {
        Product newProduct;
        try {
            newProduct = service.create(product);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch (FeignException.FeignClientException fe) {
            return Fun.getResponse("No existe el usuario", "No existe el usuario. " + fe.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Product> update(@Valid @RequestBody Product product) {
        Optional<Product> ou = service.findById(product.getProductId());
        return ou.map(u ->
                    ResponseEntity.ok(service.update(product)))
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable("id") Long productoId) {
        return service.findById(productoId)
                .map(i -> {
                    service.delete(i.getProductId());
                    return ResponseEntity.ok(i);
                })
                .orElseGet(()->ResponseEntity.notFound().build());
    }
}
