package com.pdproject.iolibrary.repository;

import com.pdproject.iolibrary.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findById(int id);
}
