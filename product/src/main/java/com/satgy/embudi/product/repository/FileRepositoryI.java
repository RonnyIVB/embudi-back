package com.satgy.embudi.product.repository;

import com.satgy.embudi.product.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepositoryI extends JpaRepository<File, Long> {

    @Query("Select f from File f where f.fileId = :fileId")
    public File findByrId(@Param("fileId") Long fileId);

    @Query("Select f from File f where f.name = :name")
    public Optional<File> findByName(@Param("name") String name);


}
