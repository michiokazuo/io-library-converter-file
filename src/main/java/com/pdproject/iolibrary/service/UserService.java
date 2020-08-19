package com.pdproject.iolibrary.service;

import com.pdproject.iolibrary.model.User;

import java.sql.SQLException;

public interface UserService {
    User signIn(String email, String password) throws SQLException;

    User signUp(User user) throws SQLException;

    User delete(int id) throws SQLException;

    User update(User user) throws SQLException;

    User findAll() throws SQLException; // admin
}
