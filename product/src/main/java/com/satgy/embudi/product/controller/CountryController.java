package com.satgy.embudi.product.controller;

import com.satgy.embudi.product.model.Country;
import com.satgy.embudi.product.service.CountryServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/country")
public class CountryController {

    @Autowired
    private CountryServiceI service;

    @GetMapping("namelike")
    public ResponseEntity<List<Country>> findByNameLike(@RequestParam(defaultValue = "") String name) {
        return ResponseEntity.ok(service.findByNameLike(name));
    }
}
