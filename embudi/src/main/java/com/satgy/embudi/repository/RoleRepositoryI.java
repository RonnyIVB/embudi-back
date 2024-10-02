package com.satgy.embudi.repository;

import com.satgy.embudi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositoryI extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
