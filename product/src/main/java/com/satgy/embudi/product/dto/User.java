package com.satgy.embudi.product.dto;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long userId;
    private String uuid;
    //private Role role;
    private Boolean enable = true;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date entryDate;
    private Date lastEntryDate;

}
