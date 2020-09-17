package com.pdproject.iolibrary.service;

import com.pdproject.iolibrary.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    UserDTO insert(UserDTO userDTO) throws Exception;

    boolean delete(int id) throws Exception;

    UserDTO update(UserDTO userDTO) throws Exception;

    List<UserDTO> findAll() throws Exception;

    UserDTO findByEmail(String email) throws Exception;

    UserDTO findById(int id) throws Exception;
}
