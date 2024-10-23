package com.satgy.embudi.product.service;

import com.satgy.embudi.product.model.Country;
import com.satgy.embudi.product.repository.CountryRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImp implements CountryServiceI {

    @Autowired
    private CountryRepositoryI repo;

    @Override
    public Optional<Country> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public List<Country> findByNameLike(String name) {
        return repo.findByNameLike(name.trim());
    }

    @Override
    public Country create(Country country) {
        return repo.save(country);
    }

    @Override
    public Country update(Country country) {
        return repo.save(country);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
