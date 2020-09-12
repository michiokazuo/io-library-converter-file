package com.pdproject.iolibrary.controller.api;

import com.pdproject.iolibrary.model.FileIO;
import com.pdproject.iolibrary.service.ConvertFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;

@RestController
@RequestMapping(path = "/api/v1/convert/*")
public class FileController {
    
    @Autowired
    private ConvertFileService convertFileService;

    @PostMapping(value = "to")
    public ResponseEntity<Resource> convertFile(@RequestParam(name = "toFormat") String toFormat,
                                                @RequestParam(name = "file") MultipartFile file){
        try {
            FileIO fileIO = convertFileService.convert(file,toFormat);
            String fileName = fileIO.getFilename();
            byte[] bytesData = fileIO.getContent();
            long contentLength = bytesData.length;
            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

            System.out.println("fileName: " + fileName + " , contentLength : " + contentLength);
            System.out.println("mediaType: " + mediaType);

            fileName = URLEncoder.encode(fileName, "UTF-8");
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                    .contentType(mediaType)
                    .contentLength(contentLength)
                    .body(new ByteArrayResource(bytesData));
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return ResponseEntity.noContent().build();
    }

}
