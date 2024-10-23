package com.satgy.embudi.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="productresource")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productresourceid")
    private Long productResourceId;

    @ManyToOne
    @JoinColumn(name = "productid", nullable = true, referencedColumnName = "productid", foreignKey=@ForeignKey(name = "FK_ProductResource_Product"))
    private Product product;

    @NotNull(message = "Por favor ingresa el orden del recurso")
    @Column(name = "order", nullable = false)
    private Short order;

    @NotNull(message = "Por favor ingresa el nombre del recurso")
    @Size(min = 3, max = 200, message = "El nombre debe tener de 3 a 200 caracteres")
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @NotNull(message = "Especifica si es Principal")
    @Column(name = "main", nullable = false)
    private Boolean main;

    //@NotNull(message = "Por favor ingresa la descripción")
    @Size(max = 250, message = "La descripción puede tener hasta 250 caracteres")
    @Column(name = "descripcion", nullable = true, length = 250)
    private String description;

    @ManyToOne
    @JoinColumn(name = "fileid", nullable = true, referencedColumnName = "fileid", foreignKey=@ForeignKey(name = "FK_ProductResource_File"))
    private File file;
}