package com.satgy.embudi.service;

import com.satgy.embudi.model.User;
import com.satgy.embudi.repository.UserRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImp implements UserServiceI{

    @Autowired
    private UserRepositoryI repo;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUuid(String uuid){
        return repo.findByUuid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email){
        return repo.findByEmail(email);
    }

    @Override
    @Transactional
    public User create(User user) {
        if (user.getUserid() == null) {
            if (user.getEntryDate() == null) user.setEntryDate(new Date());
            if (user.getLastEntryDate() == null) user.setLastEntryDate(new Date());
            UUID userId = UUID.randomUUID();
            user.setUuid(userId.toString());
            return repo.save(user);
        }
        return null; // because id must be null, if isn't then this is the wrong method
    }

    @Override
    @Transactional
    public User update(User user) {
        return repo.save(user);
    }

    @Override
    @Transactional
    public User setEnable(Long id, Boolean enable) {
        Optional<User> ou = findById(id);
        if (ou.isEmpty()) return null;
        User u = ou.get();
        u.setEnable(enable);
        return repo.save(u);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
