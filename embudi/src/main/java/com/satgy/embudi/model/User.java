package com.satgy.embudi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(
        name="\"user\"",
        indexes =
            {
                @Index(columnList = "email", name = "Index_User_Email", unique = true),
                @Index(columnList = "uuid", name = "Index_User_Uuid", unique = true)
            }
)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @NotNull(message = "Enter the uuid")
    @Size(min = 3, max = 50, message = "The uuid field must be 3 to 50 characters long")
    @Column(columnDefinition = "varchar(50) default ''", name = "uuid", nullable = false, length = 50)
    private String uuid;

    @NotNull(message = "Specify if this user is enable")
    @Column(name = "enable", nullable = false)
    private Boolean enable = true;

    @Size(min = 3, max = 25, message = "The first name must be 3 to 25 characters long")
    @Column(name = "firstname", length = 25, nullable = true)
    private String firstName;

    @Size(min = 3, max = 25, message = "The last name must be 3 to 25 characters long")
    @Column(name = "lastname", length = 25, nullable = true)
    private String lastName;

    @NotNull(message = "Please enter the email")
    @Email(message = "Please enter a valid email")
    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

    @Column(name = "entrydate", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;

    @Column(name = "lastentrydate", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastEntryDate ;

    // -------------------------------------------------- GETTERS AND SETTERS --------------------------------------------------------------------

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public @NotNull(message = "Enter the uuid") @Size(min = 3, max = 50, message = "The uuid field must be 3 to 50 characters long") String getUuid() {
        return uuid;
    }

    public void setUuid(@NotNull(message = "Enter the uuid") @Size(min = 3, max = 50, message = "The uuid field must be 3 to 50 characters long") String uuid) {
        this.uuid = uuid;
    }

    public @NotNull(message = "Specify if this user is enable") Boolean getEnable() {
        return enable;
    }

    public void setEnable(@NotNull(message = "Specify if this user is enable") Boolean enable) {
        this.enable = enable;
    }

    public @Size(min = 3, max = 25, message = "The first name must be 3 to 25 characters long") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Size(min = 3, max = 25, message = "The first name must be 3 to 25 characters long") String firstName) {
        this.firstName = firstName;
    }

    public @Size(min = 3, max = 25, message = "The last name must be 3 to 25 characters long") String getLastName() {
        return lastName;
    }

    public void setLastName(@Size(min = 3, max = 25, message = "The last name must be 3 to 25 characters long") String lastName) {
        this.lastName = lastName;
    }

    public @NotNull(message = "Please enter the email") @Email(message = "Please enter a valid email") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "Please enter the email") @Email(message = "Please enter a valid email") String email) {
        this.email = email;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getLastEntryDate() {
        return lastEntryDate;
    }

    public void setLastEntryDate(Date lastEntryDate) {
        this.lastEntryDate = lastEntryDate;
    }
}
