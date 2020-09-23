package com.pdproject.iolibrary.service_impl;

import com.pdproject.iolibrary.converter.Converter;
import com.pdproject.iolibrary.dto.UserDTO;
import com.pdproject.iolibrary.model.User;
import com.pdproject.iolibrary.repository.RoleRepository;
import com.pdproject.iolibrary.repository.UserRepository;
import com.pdproject.iolibrary.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final Converter<User, UserDTO> converter;

    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${path.avatar-default}")
    private String AVATAR_DEFAULT;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, Converter<User, UserDTO> converter, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO insert(UserDTO userDTO) throws Exception {
        UserDTO dto = null;
        if ((dto = findByEmail(userDTO.getEmail())) != null){
            return dto;
        }
        User user = converter.toModel(userDTO);
        user.setEnabled(true);
        user.setRole(roleRepository.findById(1));
        if (user.getAvatar() == null) user.setAvatar(AVATAR_DEFAULT);
        if (userDTO.getPassword() != null) user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
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
    public UserDTO updatePassword(String email, String password) throws Exception {
        User user = userRepository.findByEmailAndEnabledIsTrue(email);
        if (user != null){
            user.setPassword(passwordEncoder.encode(password));
        }
        return user != null ? converter.toDTO(userRepository.save(user)) : null;
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
        User user = userRepository.findByEmailAndEnabledIsTrue(email);
        return user != null ? converter.toDTO(user) : null;
    }

    @Override
    public UserDTO findById(int id) throws Exception {
        User user = userRepository.findByIdAndEnabledIsTrue(id);
        return user != null ? converter.toDTO(user) : null;
    }
}
