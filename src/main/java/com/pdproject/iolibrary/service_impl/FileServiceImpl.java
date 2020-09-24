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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        return (id == null || id < 0) ? null : fileIORepository.findByIdAndEnabledIsTrue(id);
    }

    @Override
    public boolean shareFiles(String email, Integer id) {
        return false;
    }

    @Override
    public FileDTO findById(Integer id) throws Exception {
        return (id == null || id < 0) ? null : converter.toDTO(fileIORepository.findByIdAndEnabledIsTrue(id));
    }

    @Override
    public List<FileDTO> findAll(String email) {
        return email == null ? null : userRepository.findByEmailAndEnabledIsTrue(email).getFileIOList()
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        List<FileDTO> fileDTOList = findAll(email);

        return fileDTOList == null ? null : fileDTOList
                .stream().filter(f -> {
                    try {
                        return (f.getFileName().toUpperCase().contains(fileName == null ? "" : fileName.toUpperCase()))
                                && f.getCreateDate().compareTo(startDate == null ? dateFormat.parse("01-01-0000") : startDate) >= 0
                                && f.getCreateDate().compareTo(endDate == null ? dateFormat.parse("31-12-9999") : endDate) <= 0;
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return true;
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<FileDTO> sortBy(String field, boolean isASC, String email) {
        Integer userId = userRepository.findByEmailAndEnabledIsTrue(email).getId();

        if (userId == null) return null;

        return fileIORepository.findAll(Sort.by(isASC ? Sort.Direction.ASC : Sort.Direction.DESC, field))
                .stream().filter(f -> (f.getCreateBy() != null && f.getCreateBy().getId().equals(userId))).collect(Collectors.toList())
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
    public boolean deleteFile(String email, Integer id) {
        Integer userId = userRepository.findByEmailAndEnabledIsTrue(email).getId();
        boolean rs = false;

        if (id != null) {
            FileIO fileIO = fileIORepository.findByIdAndEnabledIsTrue(id);
            if (fileIO.getCreateBy().getId().equals(userId)) {
                fileIO.setEnabled(false);

                rs = true;
            }

        }

        return rs;
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
        return Objects.requireNonNull(fileIORepository.findByIdAndEnabledIsTrue(id)).getContent();
    }
    // convert pdf to
    // doc, docx, ppt, pptx, xls, xlsx, encrypt, decrypt, compress
}
