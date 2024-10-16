package com.satgy.embudi.product.repository;

import com.satgy.embudi.product.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepositoryI extends JpaRepository<Country, Long> {
}
