package com.satgy.embudi.product.repository;

import com.satgy.embudi.product.model.Country;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepositoryI extends JpaRepository<Country, Long> {
    @Query("SELECT c FROM Country c " +
            "WHERE lower(c.name) like lower(concat('%', :name, '%')) " +
            "AND c.enable = true " +
            "ORDER BY c.name ASC")
    List<Country> findByNameLike(@Param("name") String name);

}
