package com.satgy.embudi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @Column(name = "roleid")
    private Long roleId; // Isn't autogenerated. The user doesn't modify this table

    @Size(min = 3, max = 25, message = "The role name must be 3 to 25 characters long")
    @Column(name = "rolename", length = 25, nullable = false)
    private String roleName;
}