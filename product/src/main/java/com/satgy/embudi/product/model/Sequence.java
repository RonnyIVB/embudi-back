package com.satgy.embudi.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="sequence")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sequenceid")
    private Long sequenceId;

    @Column(name = "value", nullable = false)
    private Integer value;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "length", nullable = false)
    private Integer length; // zeros are added to the left

    public Sequence(Integer value, String description, Integer length) {
        this.value = value;
        this.description = description;
        this.length = length;
    }
}
