package com.satgy.embudi.product.repository;

import com.satgy.embudi.product.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepositoryI extends JpaRepository<City, Long> {
}
