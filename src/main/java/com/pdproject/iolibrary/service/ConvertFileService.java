package com.pdproject.iolibrary.service;

import com.pdproject.iolibrary.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ConvertFileService {
    FileDTO convert(MultipartFile file, String toFormat) throws Exception;

    FileDTO convert(MultipartFile file, String toFormat, String password) throws Exception;
}
