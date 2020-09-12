package com.pdproject.iolibrary.service_impl;

import com.pdproject.iolibrary.model.User;
import com.pdproject.iolibrary.repository.UserRepository;
import com.pdproject.iolibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User signIn(String email, String password) throws SQLException {
        return null;
    }

    @Override
    public User signUp(User user) throws SQLException {
        return null;
    }

    @Override
    public User delete(int id) throws SQLException {
        return null;
    }

    @Override
    public User update(User user) throws SQLException {
        return null;
    }

    @Override
    public User findAll() throws SQLException {
        return null;
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
