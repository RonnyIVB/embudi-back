package com.satgy.embudi.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="file")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fileid")
    private Long fileId;

    @NotNull(message = "Por favor ingresa el nombre")
    @Size(min = 3, max = 50, message = "El nombre debe tener de 3 a 50 caracteres")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    //@NotNull(message = "Por favor ingresa el nombre")
    @Size(min = 3, max = 500, message = "El nombre original debe tener de 3 a 50 caracteres")
    @Column(name = "originalname", nullable = true, length = 500)
    private String originalName;

    //@NotNull(message = "Por favor ingresa el tipo")
    @Size(min = 1, max = 100, message = "El tipo de archivo debe tener de 1 a 100 caracteres")
    @Column(name = "type", nullable = true, length = 100)
    private String type; // file type

    @Column(name = "size", nullable = true)
    private long size; // file

    @Size(max = 100, message = "La descripción puede tener hasta 100 caracteres")
    @Column(name = "descripcion", nullable = true, length = 100)
    private String description;

    public File(String name, String originalName, String type, long size) {
        this.name = name;
        this.originalName = originalName;
        this.type = type;
        this.size = size;
    }

    public String getUriOpen() {
        return org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/openFile/")
                .path(name)
                .toUriString();
    }

    public String getUriDownload() {
        return org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/downloadFile/")
                .path(name)
                .toUriString();
    }
}
