package com.satgy.embudi.repository;

import com.satgy.embudi.model.UserPassword;
import org.springframework.transaction.annotation.Transactional; // zzzz this import could be another : import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserPasswordRepositoryI extends JpaRepository <UserPassword, Long> {
    @Query("Select up from UserPassword up Where up.user.email = :email ")
    public List<UserPassword> findByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("update UserPassword up set up.password = :pass where up.userpasswordId = :upid")
    void setPassword(@Param("upid") Long userpasswordId, @Param("pass") String password);
}
