package com.pdproject.iolibrary.controller.api;

import com.pdproject.iolibrary.dto.FileDTO;
import com.pdproject.iolibrary.model.FileIO;
import com.pdproject.iolibrary.service.ConvertFileService;
import com.pdproject.iolibrary.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Principal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/public/*")
public class FileController {

    private final ConvertFileService convertFileService;

    private final FileService fileService;

    @PostMapping("/convert/to/{toFormat}")
    public ResponseEntity<FileDTO> convertFile(@PathVariable(name = "toFormat") String toFormat,
                                               @RequestParam(name = "password", required = false) String password,
                                               @RequestParam(name = "file") MultipartFile file) {
        try {
            FileDTO fileDTO = password == null
                    ? convertFileService.convert(file, toFormat.toLowerCase()) : convertFileService.convert(file, toFormat.toLowerCase(), password);
            return ResponseEntity.ok(fileDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
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
                    .cacheControl(CacheControl.empty().cachePublic())
                    .contentType(mediaType)
                    .contentLength(contentLength)
                    .body(new ByteArrayResource(bytesData));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<FileDTO>> findAll(Authentication authentication) {
        List<FileDTO> fileDTOList = null;
        try {
            User user = (User) authentication.getPrincipal();
            fileDTOList = fileService.findAll(user.getUsername());
        } catch (Exception e) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            try {
                fileDTOList = fileService.findAll(token.getPrincipal().getAttribute("email"));
            } catch (SQLException throwables) {
                return ResponseEntity.badRequest().build();
            }
        }
        return fileDTOList != null ? ResponseEntity.ok(fileDTOList) : ResponseEntity.noContent().build();
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<FileDTO> findById(@PathVariable("id") Integer id) {
        try {
            FileDTO fileDTO = fileService.findById(id);
            return fileDTO != null ? ResponseEntity.ok(fileDTO) : ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/search")
    public ResponseEntity<List<FileDTO>> search(Authentication authentication,
                                                @RequestParam(name = "file-name", required = false) String fileName,
                                                @RequestParam(name = "start-date", required = false) Long startDate,
                                                @RequestParam(name = "end-date", required = false) Long endDate) {
        List<FileDTO> fileDTOList = null;
        String email = null;
        try {
            User user = (User) authentication.getPrincipal();
            email = user.getUsername();
        } catch (Exception e) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            email = token.getPrincipal().getAttribute("email");
        }
        if (email != null) {
            try {
                fileDTOList = fileService.search(fileName, startDate == null ? null : new Date(startDate)
                        , endDate == null ? null : new Date(endDate), email);
            } catch (Exception throwables) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
        return fileDTOList != null ? ResponseEntity.ok(fileDTOList) : ResponseEntity.noContent().build();
    }

    @PostMapping("/sort-by")
    public ResponseEntity<List<FileDTO>> sortBy(Authentication authentication,
                                                @RequestParam(name = "field", required = false) String field,
                                                @RequestParam(name = "isASC", required = false) String isASC) {
        List<FileDTO> fileDTOList = null;
        String email = null;
        try {
            User user = (User) authentication.getPrincipal();
            email = user.getUsername();
        } catch (Exception e) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            email = token.getPrincipal().getAttribute("email");
        }
        if (email != null) {
            try {
                if (field != null && isASC != null) {
                    fileDTOList = fileService.sortBy(field, Boolean.parseBoolean(isASC), email);
                } else {
                    fileDTOList = fileService.findAll(email);
                }
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
        return fileDTOList != null ? ResponseEntity.ok(fileDTOList) : ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-file/{id}")
    public ResponseEntity<String> deleteFile(Principal principal,
                                             @PathVariable("id") Integer id) {
        try {
            User user = (User) ((Authentication) principal).getPrincipal();

            return fileService.deleteFile(user.getUsername(), id) ? ResponseEntity.ok("Delete Complete") : ResponseEntity.noContent().build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/update-view-file/{id}")
    public ResponseEntity<String> updateViewFile(@PathVariable("id") Integer id) {
        try {

            return fileService.updateFile(id) ? ResponseEntity.ok("Delete Complete") : ResponseEntity.noContent().build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

}
