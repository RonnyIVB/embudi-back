package com.satgy.embudi.service;

import com.satgy.embudi.dto.Login;
import com.satgy.embudi.model.UserPassword;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserPasswordServiceI {
    public UserPassword validate(String email, String password);
    public String resetPassword(String email);
    public String changePassword(Login login);
    public Optional<UserPassword> findById(Long userpasswordId);
    public List<UserPassword> findByEmail(String email);
    public UserPassword create(Login login);
    public UserPassword update(UserPassword userPassword);
    public void delete(Long id);
    public UserDetails loadUserByUsername(String email);
}
