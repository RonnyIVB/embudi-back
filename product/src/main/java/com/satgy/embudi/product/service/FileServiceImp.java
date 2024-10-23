package com.satgy.embudi.product.service;

import com.satgy.embudi.product.model.File;
import com.satgy.embudi.product.repository.FileRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImp implements FileServiceI {

    @Autowired
    private FileRepositoryI repo;

    @Override
    public Optional<File> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<File> findByName(String name) {
        return repo.findByName(name.trim());
    }

    @Override
    public File create(File country) {
        return repo.save(country);
    }

    @Override
    public File update(File country) {
        return repo.save(country);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
