package com.satgy.embudi.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name="country")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "countryid")
    private Long countryId;

    @NotNull(message = "Por favor ingresa el nombre del país")
    @Size(min = 3, max = 50, message = "El nombre debe tener de 3 a 50 caracteres")
    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;

    @Size(max = 5, message = "El código puede tener hasta 5 caracteres")
    @Column(name = "code", nullable = true, length = 5)
    private String code;

    @NotNull(message = "Especifica si está activo")
    @Column(name = "enable", nullable = false)
    private Boolean enable;
}
