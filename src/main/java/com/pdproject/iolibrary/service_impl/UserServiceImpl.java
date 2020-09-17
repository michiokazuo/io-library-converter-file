package com.pdproject.iolibrary.service_impl;

import com.pdproject.iolibrary.converter.Converter;
import com.pdproject.iolibrary.dto.UserDTO;
import com.pdproject.iolibrary.model.User;
import com.pdproject.iolibrary.repository.RoleRepository;
import com.pdproject.iolibrary.repository.UserRepository;
import com.pdproject.iolibrary.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private Converter<User, UserDTO> converter;

    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDTO insert(UserDTO userDTO) throws SQLException {
        if (checkEmailExist(userDTO.getEmail())){
            return null;
        }
        User user = converter.toModel(userDTO);
        user.setEnabled(true);
        user.setRole(roleRepository.findById(1));
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return converter.toDTO(userRepository.save(user));
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public UserDTO update(UserDTO userDTO) throws SQLException {
        User user = converter.toModel(userDTO);
        return converter.toDTO(userRepository.save(user));
    }

    @Override
    public List<UserDTO> findAll() throws SQLException {
        return userRepository.findAll().stream()
                .map(user -> converter.toDTO(user)).collect(Collectors.toList());
    }

    public UserDTO findByEmail(String email){
        return converter.toDTO(userRepository.findByEmail(email));
    }

    private boolean checkEmailExist(String email){
        User result = userRepository.findAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
        return result != null;
    }
}
