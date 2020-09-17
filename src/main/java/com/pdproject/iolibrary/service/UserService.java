package com.pdproject.iolibrary.service;

import com.pdproject.iolibrary.dto.UserDTO;
import com.pdproject.iolibrary.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    UserDTO insert(UserDTO userDTO) throws SQLException;

    boolean delete(int id) throws SQLException;

    UserDTO update(UserDTO userDTO) throws SQLException;

    List<UserDTO> findAll() throws SQLException;

    UserDTO findByEmail(String email) throws SQLException;
}
