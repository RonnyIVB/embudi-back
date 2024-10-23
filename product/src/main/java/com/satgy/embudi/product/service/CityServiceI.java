package com.satgy.embudi.product.service;

import com.satgy.embudi.product.model.City;

import java.util.List;
import java.util.Optional;

public interface CityServiceI {
    public Optional<City> findById(Long id);
    public List<City> findByCountryIdAndNameLike(Long countryId, String name);
    public City create(City city);
    public City update(City city);
    public void delete(Long id);
}
