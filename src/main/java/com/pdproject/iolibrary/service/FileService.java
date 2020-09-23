package com.pdproject.iolibrary.service;

import com.pdproject.iolibrary.dto.FileDTO;
import com.pdproject.iolibrary.model.FileIO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface FileService {
    FileIO downFile(Integer id) throws SQLException;

    boolean shareFiles(String email, Integer id) throws SQLException;

    FileDTO findById(Integer id) throws Exception;

    List<FileDTO> findAll(String email) throws SQLException;

    List<FileDTO> search(String fileName, Date startDate, Date endDate, String email) throws SQLException;

    List<FileDTO> sortBy(String field, boolean isASC, String email) throws SQLException;

    boolean deleteFile(String email, Integer id) throws SQLException;

    boolean printFile(Integer id) throws SQLException;

    boolean printFile(FileDTO t) throws SQLException;

    byte[] getContentFile(Integer id) throws SQLException;
}
