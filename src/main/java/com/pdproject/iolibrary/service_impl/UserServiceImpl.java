package com.pdproject.iolibrary.service_impl;

import com.pdproject.iolibrary.converter.Converter;
import com.pdproject.iolibrary.dto.UserDTO;
import com.pdproject.iolibrary.model.User;
import com.pdproject.iolibrary.repository.RoleRepository;
import com.pdproject.iolibrary.repository.UserRepository;
import com.pdproject.iolibrary.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public UserDTO insert(UserDTO userDTO) throws Exception {
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
    public boolean delete(int id) {
        boolean result = true;
        try {
            User user = userRepository.findByIdAndEnabledIsTrue(id);
            user.setEnabled(false);
            userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public UserDTO update(UserDTO userDTO) throws Exception {
        User user = userRepository.findByIdAndEnabledIsTrue(userDTO.getId());
        user.setName(userDTO.getName());
        user.setAvatar(userDTO.getAvatar());
        user.setEnabled(userDTO.getEnabled());
        return converter.toDTO(userRepository.save(user));
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAllByEnabledIsTrue().stream()
                .map(user -> {
                    try {
                        return converter.toDTO(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toList());
    }

    public UserDTO findByEmail(String email) throws Exception {
        return converter.toDTO(userRepository.findByEmailAndEnabledIsTrue(email));
    }

    @Override
    public UserDTO findById(int id) throws Exception {
        return converter.toDTO(userRepository.findByIdAndEnabledIsTrue(id));
    }

    private boolean checkEmailExist(String email){
        User result = userRepository.findAllByEnabledIsTrue().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
        return result != null;
    }
}
