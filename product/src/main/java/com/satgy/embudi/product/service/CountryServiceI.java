package com.satgy.embudi.product.service;

import com.satgy.embudi.product.model.Country;

import java.util.List;
import java.util.Optional;

public interface CountryServiceI {
    public Optional<Country> findById(Long id);
    public List<Country> findByNameLike(String name);
    public Country create(Country country);
    public Country update(Country country);
    public void delete(Long id);
}
