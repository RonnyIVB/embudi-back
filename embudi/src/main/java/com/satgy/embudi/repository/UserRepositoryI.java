package com.satgy.embudi.repository;

import com.satgy.embudi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryI extends JpaRepository <User, Long> {

    User findByEmail(String email);

    User findByUuid(String uuid);
}
