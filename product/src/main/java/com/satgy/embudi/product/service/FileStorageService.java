package com.satgy.embudi.product.service;

import com.satgy.embudi.product.exception.FileStorageException;
import com.satgy.embudi.product.exception.MyFileNotFoundException;
import com.satgy.embudi.product.general.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     *
     * @param file
     * @param nombreNuevo un número secuencial que se asigna al archivo para asegurar que no se repitan los nombres
     * @return
     */
    public String storeFile(MultipartFile file, String nombreNuevo) {
        // Normalize file name
        String fileName;
        if (nombreNuevo == null) fileName = StringUtils.cleanPath(file.getOriginalFilename());
        else fileName = nombreNuevo;
        //String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public boolean deleteFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                java.io.File fileToDelete = new java.io.File(filePath.toUri());
                //return fileToDelete.delete();
                org.apache.commons.io.FileUtils.forceDelete(fileToDelete);
                return true;
            } else {
                // I commented this throws because don't give an error if this file can't be deleted
                //throw new MyFileNotFoundException("Archivo no encontrado " + fileName);
            }
        } catch (MalformedURLException ex) {
            // I commented this throw because don't give me an error if the file can't be deleted
            //throw new MyFileNotFoundException("Archivo no encontrado " + fileName, ex);
        } catch (IOException ex) {
            // I commented this throw because don't give me an error if the file can't be deleted
            //Logger.getLogger(FileStorageService.class.getName()).log(Level.SEVERE, null, ex);
            //throw new MyFileNotFoundException("No se pudo eliminar el archivo (" + fileName + ") talvez está siendo utilizado", ex);
        }
        return false;
    }
}

