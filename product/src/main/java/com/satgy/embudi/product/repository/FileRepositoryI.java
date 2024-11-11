package com.satgy.embudi.product.repository;

import com.satgy.embudi.product.model.File;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepositoryI extends JpaRepository<File, Long> {

    @Query("Select f from File f where f.fileId = :fileId")
    public Optional<File> findById(@Param("fileId") Long fileId);

    @Query("Select f from File f where f.name = :name")
    public Optional<File> findByName(@Param("name") String name);

    // delete product and all their images/videos
    @Transactional
    @Modifying
    @Query("delete from File f where f.fileId IN " +
            "(select pr.file.fileId from ProductResource pr where pr.product.productId = :id)")
    public int deleteByProduct(@Param("id") Long productId);

    // delete an image
    @Transactional
    @Modifying
    @Query("delete from File f where f.fileId IN " +
            "(select pr.file.fileId from ProductResource pr where pr.productResourceId = :id)")
    public int deleteByProductResource(@Param("id") Long productResourceId);

    @Query("Select f From File f Where f.fileId IN " +
            "(select pr.file.fileId from ProductResource pr where pr.productResourceId = :id)")
    public Optional<File> findByProductResource(@Param("id") Long productResourceId);
}
