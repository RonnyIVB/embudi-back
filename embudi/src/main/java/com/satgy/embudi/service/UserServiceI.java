package com.satgy.embudi.service;

import com.satgy.embudi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceI {
    public List<User> findAll();
    public Optional<User> findById(Long id);
    public Optional<User> findByUuid(String uuid);
    public Optional<User> findByEmail(String email);
    public User create(User user);
    public User update(User user);
    public User setEnable(Long id, Boolean enable);
    public void delete(Long id);
}
