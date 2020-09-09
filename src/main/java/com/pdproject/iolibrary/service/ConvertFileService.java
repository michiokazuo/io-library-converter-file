package com.pdproject.iolibrary.service;

import com.pdproject.iolibrary.model.FileIO;

import java.sql.SQLException;

public interface ConvertFileService {
    FileIO convert(String pathFile, String toFormat) throws SQLException;

    FileIO protect(String pathFile, String toFormat, String password);
}
