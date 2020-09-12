package com.pdproject.iolibrary.repository;

import com.pdproject.iolibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    User findByEmail(String email);
}
