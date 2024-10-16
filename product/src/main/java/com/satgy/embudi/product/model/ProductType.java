package com.satgy.embudi.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name="producttype")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producttypeid")
    private Long productTypeId;

    @Size(min = 3, max = 25, message = "The first name must be 3 to 25 characters long")
    @Column(name = "firstname", length = 25, nullable = true)
    private String firstName;
}
