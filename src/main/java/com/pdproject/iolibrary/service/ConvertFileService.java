package com.pdproject.iolibrary.service;

import com.pdproject.iolibrary.model.FileIO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public interface ConvertFileService {
    FileIO convert(MultipartFile file, String toFormat) throws IOException, ExecutionException, InterruptedException;

    FileIO convert(MultipartFile file, String toFormat, String password) throws IOException, ExecutionException, InterruptedException;
}
