package com.satgy.embudi.dto;

public class Login {
    @Override
    public String toString(){ return "Email: " + email + " ; Password: " + password; }

    private String email;
    private String password;
    private String newPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}