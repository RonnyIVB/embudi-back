package com.satgy.embudi.repository;

import com.satgy.embudi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryI extends JpaRepository <User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUuid(String uuid);
}
