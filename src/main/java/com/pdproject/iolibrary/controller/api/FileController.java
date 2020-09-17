package com.pdproject.iolibrary.controller.api;

import com.pdproject.iolibrary.model.FileIO;
import com.pdproject.iolibrary.model.JsonResult;
import com.pdproject.iolibrary.repository.FileIORepository;
import com.pdproject.iolibrary.service.ConvertFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URLDecoder;
import java.net.URLEncoder;

@RestController
@RequestMapping(path = "/api/v1/convert/*")
public class FileController {

    @Autowired
    private JsonResult jsonResult;

    @Autowired
    private ConvertFileService convertFileService;

    @Autowired
    private FileIORepository fileIORepository;

    @PostMapping(value = {"to/{toFormat}/{password}", "to/{toFormat}"})
    public JsonResult convertFile(@PathVariable(name = "toFormat") String toFormat,
                                  @PathVariable(name = "password", required = false) String password,
                                  @RequestParam(name = "file") MultipartFile file) {
        JsonResult rs = null;

        try {
            FileIO fileIO = password == null
                    ? convertFileService.convert(file, toFormat.toLowerCase()) : convertFileService.convert(file, toFormat.toLowerCase(), password);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/convert/download/")
                    .path(fileIO.getId() + "")
                    .toUriString();
            rs = jsonResult.jsonSuccess(fileDownloadUri);

        } catch (Exception e) {
            e.printStackTrace();
            rs = jsonResult.jsonFailure("Download Fail");
        }
        return rs;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFileConvert(@PathVariable("id") int id) {
        try {
            FileIO fileIO = fileIORepository.findById(id).get();

            String fileName = fileIO.getFilename();
            byte[] bytesData = fileIO.getContent();
            long contentLength = bytesData.length;
            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

            System.out.println("fileName: " + fileName + " , contentLength : " + contentLength);
            System.out.println("mediaType: " + mediaType);

            fileName = URLEncoder.encode(fileName, "UTF-8");
            fileName = URLDecoder.decode(fileName, "ISO8859_1");
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                    .contentType(mediaType)
                    .contentLength(contentLength)
                    .body(new ByteArrayResource(bytesData));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.noContent().build();
    }

}
