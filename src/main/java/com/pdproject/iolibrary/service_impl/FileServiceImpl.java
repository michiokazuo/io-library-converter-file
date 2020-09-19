package com.pdproject.iolibrary.service_impl;

import com.pdproject.iolibrary.converter.Converter;
import com.pdproject.iolibrary.dto.FileDTO;
import com.pdproject.iolibrary.model.FileIO;
import com.pdproject.iolibrary.repository.FileIORepository;
import com.pdproject.iolibrary.repository.UserRepository;
import com.pdproject.iolibrary.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileIORepository fileIORepository;

    private final UserRepository userRepository;

    private final Converter<FileIO, FileDTO> converter;

    @Override
    public FileIO downFile(Integer id) {
        return fileIORepository.findById(id).orElse(null);
    }

    @Override
    public boolean shareFiles(List<Integer> listId) {
        return false;
    }

    @Override
    public FileDTO findById(Integer id) throws Exception {
        return converter.toDTO(fileIORepository.findById(id).orElse(null));
    }

    @Override
    public List<FileDTO> findAll(String email) {
        return userRepository.findByEmailAndEnabledIsTrue(email).getFileIOList()
                .stream().map(file -> {
                    try {
                        return converter.toDTO(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public List<FileDTO> search(String fileName, Date startDate, Date endDate, String email) {
        return userRepository.findByEmailAndEnabledIsTrue(email).getFileIOList()
                .stream().filter(f -> (f.getFilename().toUpperCase().contains(fileName.toUpperCase())) && f.getCreateDate().compareTo(startDate) >= 0 && f.getCreateDate().compareTo(endDate) <= 0).collect(Collectors.toList())
                .stream().map(file -> {
                    try {
                        return converter.toDTO(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public List<FileDTO> sortBy(String field, boolean isASC, String email) {
        return fileIORepository.findAll(Sort.by(isASC ? Sort.Direction.ASC : Sort.Direction.DESC, field))
                .stream().filter(f -> (f.getCreateBy() != null && f.getCreateBy().getId().equals(userRepository.findByEmailAndEnabledIsTrue(email).getId()))).collect(Collectors.toList())
                .stream().map(file -> {
                    try {
                        return converter.toDTO(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public boolean deleteFile(Integer id) {
        return false;
    }

    @Override
    public boolean printFile(Integer id) {
        return false;
    }

    @Override
    public boolean printFile(FileDTO t) {
        return false;
    }

    @Override
    public byte[] getContentFile(Integer id) {
        return Objects.requireNonNull(fileIORepository.findById(id).orElse(null)).getContent();
    }
    // convert pdf to
    // doc, docx, ppt, pptx, xls, xlsx, encrypt, decrypt, compress
}
