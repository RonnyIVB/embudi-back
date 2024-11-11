package com.satgy.embudi.product.model;

import com.satgy.embudi.product.dto.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productid")
    private Long productId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "cityid", nullable = true, referencedColumnName = "cityid", foreignKey=@ForeignKey(name = "FK_Product_City"))
    private City city;

    @NotNull(message = "Por favor define el usuario que agrega el producto")
    @Column(name = "userid", nullable = false)
    private Long userId; // User in another microservice

    @Transient
    private User user;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "operation", nullable = false)
    private ProductOperation operation;

    @ManyToOne
    @NotNull(message = "Por favor defina el tipo de inmueble")
    @JoinColumn(name = "producttypeid", nullable = false, referencedColumnName = "producttypeid", foreignKey=@ForeignKey(name = "FK_Product_ProductType"))
    private ProductType type;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "stratum", nullable = true)
    private ProductStratum stratum;

    @Size(max = 20, message = "El código debe tener hasta 20 caracteres")
    @Column(name = "code", length = 20, nullable = true)
    private String code;

    @Size(min = 10, max = 100, message = "El nombre debe tener de 10 hasta 100 caracteres")
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Size(max = 250, message = "La referencia debe tener hasta 250 caracteres")
    @Column(name = "reference", length = 250, nullable = true)
    private String reference;

    @Size(max = 500, message = "La descripción larga debe tener hasta 500 caracteres")
    @Column(name = "longdescription", length = 500, nullable = true)
    private String longDescription;

    @Size(max = 500, message = "Los comentarios deben tener hasta 500 caracteres")
    @Column(name = "comments", length = 500, nullable = true)
    private String comments;

    @Size(max = 100, message = "El nombre del propietario debe tener hasta 100 caracteres")
    @Column(name = "ownername", length = 100, nullable = true)
    private String ownerName;

    @Size(max = 100, message = "El teléfono del propietario debe tener hasta 100 caracteres")
    @Column(name = "ownerphone", length = 100, nullable = true)
    private String ownerPhone;

    @Column(name = "exclusivity", nullable = true)
    private Boolean exclusivity;

    @Column(name = "commission", nullable = true, precision = 6, scale = 2)
    private BigDecimal commission;

    @Column(name = "landarea", nullable = true, precision = 6, scale = 2)
    private BigDecimal landArea; // Área de terreno

    @Column(name = "livablearea", nullable = true, precision = 6, scale = 2)
    private BigDecimal livableArea; // Área de construcción

    @Column(name = "bathrooms", nullable = true)
    private Short bathrooms;

    @Column(name = "bedrooms", nullable = true)
    private Short bedrooms;

    @Column(name = "parkingspots", nullable = true)
    private Short parkingSpots; // garages

    @Size(max = 30, message = "Las coordenadas ni pueden tener más de 30 caracteres")
    @Column(name = "coordinates", length = 30, nullable = true)
    private String coordinates;

    @NotNull(message = "Especifica el precio al público")
    @Column(name = "publicprice", nullable = false, precision = 20, scale = 2)
    private BigDecimal publicPrice; // precio al público para mostrar

    @NotNull(message = "Especifica el precio al público")
    @Column(name = "minprice", nullable = false, precision = 20, scale = 2)
    private BigDecimal minPrice; // precio mínimo de venta, oculto

    @Size(max = 50, message = "El sector debe tener hasta 50 caracteres")
    @Column(name = "sector", length = 50, nullable = true)
    private String sector;

    @Size(max = 100, message = "La dirección debe tener hasta 100 caracteres")
    @Column(name = "address", length = 100, nullable = true)
    private String address;
}
