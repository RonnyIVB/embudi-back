package com.satgy.embudi.product.service;

import com.satgy.embudi.product.model.File;

import java.util.List;
import java.util.Optional;

public interface FileServiceI {
    public Optional<File> findById(Long id);
    public Optional<File> findByProductResource(Long productResourceId);
    public Optional<File> findByName(String name);
    public File create(File file);
    public File update(File file);
    public void delete(Long id);
}
