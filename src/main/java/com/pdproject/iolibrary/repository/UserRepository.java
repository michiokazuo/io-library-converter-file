package com.pdproject.iolibrary.repository;

import com.pdproject.iolibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    User findByEmailAndEnabledIsTrue(String email);

    User findByIdAndEnabledIsTrue(int id);

    List<User> findAllByEnabledIsTrue();
}
