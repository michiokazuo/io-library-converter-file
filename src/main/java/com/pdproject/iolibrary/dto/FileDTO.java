package com.pdproject.iolibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO extends BaseDTO {
    private String fileName;
    private String urlDownload;
}
