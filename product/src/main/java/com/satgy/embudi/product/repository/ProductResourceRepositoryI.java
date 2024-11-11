package com.satgy.embudi.product.repository;

import com.satgy.embudi.product.model.ProductResource;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductResourceRepositoryI extends JpaRepository<ProductResource, Long> {

    @Query("Select r from ProductResource r "
            + "where r.product.productId = :id "
            + "order by r.order asc")
    public List<ProductResource> findByProduct(@Param("id") Long productId);

    @Query("Select Coalesce(max(order), 0) from ProductResource r "
            + "where r.product.productId = :id")
    public Short findMaxOrderByProduct(@Param("id") Long productId);

    @Transactional
    @Modifying
    @Query("delete from ProductResource r where r.product.productId = :id")
    public int deleteByProduct(@Param("id") Long productId);

    @Transactional
    @Modifying
    @Query("Update ProductResource r Set r.main = false Where r.product.productId = :id")
    public void setAllMainFalseByProduct(@Param("id") Long productId);

    @Transactional
    @Modifying
    @Query("Update ProductResource r Set r.main = true Where r.productResourceId = :id")
    public void setMainTrueById(@Param("id") Long id);
}
