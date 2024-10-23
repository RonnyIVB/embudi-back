package com.satgy.embudi.product.controller;

import com.satgy.embudi.product.model.City;
import com.satgy.embudi.product.service.CityServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/city")
public class CityController {

    @Autowired
    private CityServiceI service;

    @GetMapping("namelike")
    public ResponseEntity<List<City>> get(
            @RequestParam(defaultValue = "1", name = "countryid") Long countryId,
            @RequestParam(defaultValue = "") String name
    ) {
        return ResponseEntity.ok(service.findByCountryIdAndNameLike(countryId, name));
    }
}
