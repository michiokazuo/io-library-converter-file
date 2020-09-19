package com.pdproject.iolibrary.converter;

import com.pdproject.iolibrary.dto.FileDTO;
import com.pdproject.iolibrary.dto.UserDTO;
import com.pdproject.iolibrary.model.FileIO;
import com.pdproject.iolibrary.model.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserConverter implements Converter<User, UserDTO> {

    private final ModelMapper modelMapper;

    private final Converter<FileIO, FileDTO> converter;

    @Override
    public UserDTO toDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setPassword("********");
        userDTO.setPasswordConfirm("********");
        if (user.getFileIOList() != null){
            List<FileDTO> fileDTOList =
                    user.getFileIOList().stream()
                            .map(fileIO -> {
                                try {
                                    return converter.toDTO(fileIO);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            })
                            .collect(Collectors.toList());
            userDTO.setFileDTOList(fileDTOList);
        }
        return userDTO;
    }

    @Override
    public User toModel(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
