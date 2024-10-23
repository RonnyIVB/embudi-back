package com.satgy.embudi.product.service;

import com.satgy.embudi.product.model.File;

import java.util.List;
import java.util.Optional;

public interface FileServiceI {
    public Optional<File> findById(Long id);
    public Optional<File> findByName(String name);
    public File create(File country);
    public File update(File country);
    public void delete(Long id);
}
