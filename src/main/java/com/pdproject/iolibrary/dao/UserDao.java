package com.pdproject.iolibrary.dao;

import com.pdproject.iolibrary.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    List<User> getList(ResultSet resultSet) throws SQLException;

    User getObject(ResultSet resultSet) throws SQLException;

    User find(String email, String password) throws SQLException;

    User findAll() throws SQLException; // admin

    User insert(User user) throws SQLException;

    User delete(int id) throws SQLException;

    User update(User user) throws SQLException;
}
