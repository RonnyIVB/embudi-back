package com.satgy.embudi.product.service;

import com.satgy.embudi.product.model.File;
import com.satgy.embudi.product.repository.FileRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileServiceImp implements FileServiceI {

    @Autowired
    private FileRepositoryI repo;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public Optional<File> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<File> findByProductResource(Long productResourceId) {
        return repo.findByProductResource(productResourceId);
    }

    @Override
    public Optional<File> findByName(String name) {
        return repo.findByName(name.trim());
    }

    @Override
    public File create(File file) {
        return repo.save(file);
    }

    @Override
    public File update(File file) {
        return repo.save(file);
    }

    @Override
    public void delete(Long id) {
        Optional<File> of = repo.findById(id);
        if (of.isPresent()){
            fileStorageService.deleteFileAsResource(of.get().getName());
            repo.deleteById(id);
        }
    }
}
