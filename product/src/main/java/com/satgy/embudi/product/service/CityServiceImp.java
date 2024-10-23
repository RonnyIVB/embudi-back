package com.satgy.embudi.product.service;

import com.satgy.embudi.product.model.City;
import com.satgy.embudi.product.repository.CityRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImp implements CityServiceI {

    @Autowired
    private CityRepositoryI repo;

    @Override
    public Optional<City> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public List<City> findByCountryIdAndNameLike(Long countryId, String name) {
        return repo.findByCountryIdAndNameLike(countryId, name.trim());
    }

    @Override
    public City create(City city) {
        return repo.save(city);
    }

    @Override
    public City update(City city) {
        return repo.save(city);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
