package com.satgy.embudi.product.controller;

import com.satgy.embudi.product.general.Str;
import com.satgy.embudi.product.general.Par;
import com.satgy.embudi.product.model.File;
import com.satgy.embudi.product.service.FileServiceI;
import com.satgy.embudi.product.service.FileStorageService;
import com.satgy.embudi.product.service.SequenceServiceI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/file")
@CrossOrigin("*")
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileServiceI service;

    @Autowired
    private SequenceServiceI secuenciaService;

    @PostMapping("/uploadFile")
    public ResponseEntity<File> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(service.create(myUploadFile(file)));
    }

    private File myUploadFile (MultipartFile file) {
        String originalName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
        String extension = Str.getExtension(org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename()));
        if (extension == null) extension = "";
        else extension = "." + extension;

        String name = secuenciaService.getValueStr(Par.FILE_SEQUENCE) + extension;
        String fileName = fileStorageService.storeFile(file, name);

        File File = new File(fileName, originalName, file.getContentType(), file.getSize());
        secuenciaService.increase(Par.FILE_SEQUENCE);
        return service.create(File);
    }

    @PostMapping("/uploadMultipleFiles")
    public ResponseEntity<List<File>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return ResponseEntity.ok(
                Arrays.stream(files)
                .map(this::myUploadFile)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        return getFile(fileName, request, true);
    }

    @GetMapping("/openFile/{fileName:.+}")
    public ResponseEntity<Resource> openFile(@PathVariable String fileName, HttpServletRequest request) {
        return getFile(fileName, request, false);
    }

    private ResponseEntity<Resource> getFile(String fileName, HttpServletRequest request, boolean isForDownload){
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        Optional<File> of = service.findByName(fileName);
        if (of.isEmpty()) return ResponseEntity.notFound().build();
        String originalName = of.get().getOriginalName();
        if (originalName == null) originalName = fileName;

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        if (isForDownload) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    // así quedaría para que se decargue con el nombre de File que está almacenado
                    //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")

                    // uso la palabra attachment para que se descargue el File
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalName + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    // así quedaría para que se decargue con el nombre de File que está almacenado
                    //.header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + resource.getFilename() + "\"")

                    // en este caso no quiero descargar, por eso quito la palabra attachment
                    .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + originalName + "\"")
                    .body(resource);
        }
    }

    @PutMapping
    public ResponseEntity<File> update (@Valid @RequestBody File File){
        return (ResponseEntity<File>) service.findById(File.getFileId())
                .map(a -> ResponseEntity.ok(service.update(File)))
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<File> delete(@PathVariable("id") Long id){
        // Isn't working the method service.findById
        /*return (ResponseEntity<File>) service.findById(id)
                .map(arch -> {
                            service.delete(id);
                            return ResponseEntity.ok(arch);
                        })
                .orElseGet(() -> ResponseEntity.notFound().build());
                */

        Optional<File> of = service.findById(id);
        if (of.isPresent()) {
            service.delete(of.get().getFileId());
            return ResponseEntity.ok(of.get());
        }
        else return ResponseEntity.notFound().build();
    }
}
