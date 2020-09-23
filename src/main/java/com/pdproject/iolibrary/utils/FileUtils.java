package com.pdproject.iolibrary.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUtils {
    private String pathFile;
    private String fileName;
    private String typeFile;
    private long size;

    @Value("${file.upload-avatar}")
    private String pathSaveFile;

    @Value("${path.avatar}")
    private String pathFileGet;

    public void storeFile(MultipartFile file) throws IOException {
        this.fileName = file.getOriginalFilename();
        this.pathFileGet += fileName;
        this.pathFile = pathSaveFile + "/" + fileName;
        this.typeFile = getTypeFile(fileName);
        this.size = file.getSize();
        File fileSave = new File(pathFile);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileSave));
        bos.write(file.getBytes());
        bos.close();
        this.pathFile = pathFileGet + "/" + fileName;
    }

    private String getTypeFile(String fileName){
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
