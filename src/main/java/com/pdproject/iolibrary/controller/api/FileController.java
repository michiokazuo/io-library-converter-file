package com.pdproject.iolibrary.controller.api;

import com.pdproject.iolibrary.dto.FileDTO;
import com.pdproject.iolibrary.model.FileIO;
import com.pdproject.iolibrary.model.ResponseMessage;
import com.pdproject.iolibrary.service.ConvertFileService;
import com.pdproject.iolibrary.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/public/*")
public class FileController {

    private final ResponseMessage message;

    private final ConvertFileService convertFileService;

    private final FileService fileService;

    @PostMapping("/convert/to/{toFormat}")
    public ResponseMessage convertFile(@PathVariable(name = "toFormat") String toFormat,
                                       @RequestParam(name = "password", required = false) String password,
                                       @RequestParam(name = "file") MultipartFile file) {
        ResponseMessage rs = null;

        try {
            FileDTO fileDTO = password == null
                    ? convertFileService.convert(file, toFormat.toLowerCase()) : convertFileService.convert(file, toFormat.toLowerCase(), password);

            rs = message.successResponse("success", fileDTO);

        } catch (Exception e) {
            e.printStackTrace();
            rs = message.failResponse("Download Fail");
        }
        return rs;
    }

    @GetMapping("/file-download/{file-name}/{id}")
    public ResponseEntity<Resource> downloadFileConvert(@PathVariable("id") int id,
                                                        @PathVariable("file-name") String name) {
        try {
            FileIO fileIO = fileService.downFile(id);

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

    @GetMapping("/find-all")
    public ResponseMessage findAll(Principal principal) {
        ResponseMessage rs = null;

        try {
            User user = (User) ((Authentication) principal).getPrincipal();

            List<FileDTO> fileDTOList = fileService.findAll(user.getUsername());

            rs = fileDTOList != null ? message.successResponse("success", fileDTOList) : message.successResponse("success", "No Data");

        } catch (Exception e) {
            e.printStackTrace();
            rs = message.failResponse("Find All Error");
        }

        return rs;
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseMessage findById(@PathVariable("id") Integer id) {
        ResponseMessage rs = null;

        try {
            FileDTO fileDTO = fileService.findById(id);

            rs = fileDTO != null ? message.successResponse("success", fileDTO) : message.successResponse("success", "No Data");

        } catch (Exception e) {
            e.printStackTrace();
            rs = message.failResponse("Find By Id Error");
        }

        return rs;
    }

    @GetMapping("/search/{file-name}/{start-date}/{end-date}")
    public ResponseMessage search(Principal principal,
                                  @PathVariable("file-name") String fileName,
                                  @PathVariable("start-date") String startDate,
                                  @PathVariable("end-date") String endDate) {
        ResponseMessage rs = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            User user = (User) ((Authentication) principal).getPrincipal();

            List<FileDTO> fileDTOList = fileService.search(fileName, dateFormat.parse(startDate)
                    , dateFormat.parse(endDate), user.getUsername());

            rs = fileDTOList != null ? message.successResponse("success", fileDTOList) : message.successResponse("success", "No Data");

        } catch (Exception e) {
            e.printStackTrace();
            rs = message.failResponse("Find By Id Error");
        }

        return rs;
    }

    @GetMapping("/sort-by/{field}/{isASC}")
    public ResponseMessage sortBy(Principal principal,
                                  @PathVariable(name = "field") String field,
                                  @PathVariable(name = "isASC") String isASC) {
        ResponseMessage rs = null;

        try {
            User user = (User) ((Authentication) principal).getPrincipal();

            List<FileDTO> fileDTOList = null;

            if (isASC.equals("true") || isASC.equals("false")) {
                fileDTOList = fileService.sortBy(field, Boolean.parseBoolean(isASC), user.getUsername());
            } else {
                fileDTOList = fileService.findAll(user.getUsername());
            }

            rs = fileDTOList != null ? message.successResponse("success", fileDTOList) : message.successResponse("success", "No Data");
        } catch (Exception e) {
            e.printStackTrace();
            rs = message.failResponse("Find By Id Error");
        }

        return rs;
    }

    @GetMapping("/print-file")
    public ResponseMessage printFile() {
        ResponseMessage rs = null;

        try {

        } catch (Exception e) {
            e.printStackTrace();
            rs = message.failResponse("Find By Id Error");
        }

        return rs;
    }

    @DeleteMapping("/delete-file/{id}")
    public ResponseMessage deleteFile() {
        ResponseMessage rs = null;

        try {

        } catch (Exception e) {
            e.printStackTrace();
            rs = message.failResponse("Find By Id Error");
        }

        return rs;
    }

    @GetMapping("/share-files")
    public ResponseMessage shareFiles() {
        ResponseMessage rs = null;

        try {

        } catch (Exception e) {
            e.printStackTrace();
            rs = message.failResponse("Find By Id Error");
        }

        return rs;
    }
}
