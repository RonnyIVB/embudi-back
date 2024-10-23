package com.satgy.embudi.product.repository;

import com.satgy.embudi.product.model.City;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepositoryI extends JpaRepository<City, Long> {
    @Query("SELECT c FROM City c " +
            "WHERE c.country.countryId = :countryId " +
            "AND lower(c.name) like lower(concat('%', :name, '%')) " +
            "AND c.enable = true " +
            "ORDER BY lower(c.name) ASC")
    List<City> findByCountryIdAndNameLike(@Param("countryId") Long countryId, @Param("name") String name);
}
