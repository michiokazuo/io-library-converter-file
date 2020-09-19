package com.pdproject.iolibrary.service_impl;

import com.convertapi.client.*;
import com.pdproject.iolibrary.converter.Converter;
import com.pdproject.iolibrary.dto.FileDTO;
import com.pdproject.iolibrary.model.FileIO;
import com.pdproject.iolibrary.repository.FileIORepository;
import com.pdproject.iolibrary.service.ConvertFileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ConvertFileServiceImpl implements ConvertFileService {

    private final Converter<FileIO, FileDTO> converter;

    private final FileIORepository fileIORepository;

    @Value("${file.upload-convert-dir}")
    private String DIR_SAVE;

    @Value("${convert-api.secret}")
    private String CONVERT_API_SECRET;

    public ConvertFileServiceImpl(Converter<FileIO, FileDTO> converter, FileIORepository fileIORepository) {
        this.converter = converter;
        this.fileIORepository = fileIORepository;
    }

    private File getDataForFileIO(ConversionResult conversionResult, String nameFile) throws IOException, ExecutionException, InterruptedException {

        File fileResult;
        Integer numberOfFiles = conversionResult.fileCount();

        if (numberOfFiles == 1) {
            ConversionResultFile conversionResultFile = conversionResult.getFile(0);
            fileResult = new File(DIR_SAVE + "/" + conversionResultFile.getName());
            FileOutputStream outputStream = new FileOutputStream(fileResult);
            IOUtils.copy(conversionResultFile.asStream().get(), outputStream);
            outputStream.close();
        } else {
            fileResult = new File(DIR_SAVE + "/" + nameFile + ".zip");
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(fileResult));
            for (int i = 0; i < numberOfFiles; i++) {
                ConversionResultFile conversionResultFile = conversionResult.getFile(i);
                ZipEntry zipEntry = new ZipEntry(conversionResultFile.getName());
                zipOut.putNextEntry(zipEntry);
                IOUtils.copy(conversionResultFile.asStream().get(), zipOut);
            }
            zipOut.close();
        }

        return fileResult;
    }

    @Override
    public FileDTO convert(MultipartFile file, String toFormat) throws Exception {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String nameFile = fileName.substring(0, fileName.lastIndexOf("."));
        String fromFormat = fileName.substring(fileName.lastIndexOf(".") + 1);

        // convert by convertapi
        Config.setDefaultSecret(CONVERT_API_SECRET);
        ConversionResult conversionResult =
                ConvertApi.convert(fromFormat, toFormat, new Param("file", file.getInputStream(), fileName))
                        .get();

        File fileResult = getDataForFileIO(conversionResult, nameFile);

        FileIO fileIOResult = new FileIO();
        fileIOResult.setFilename((toFormat.equals("compress") ? "(Compressed) " : "(Converted) ") + fileResult.getName());
        fileIOResult.setContent(Files.readAllBytes(fileResult.toPath()));
        fileIOResult.setEnabled(true);

        System.out.println(fileResult.delete() ? "Đã xóa File converted" : "Xóa file converted thất bại");

        return converter.toDTO(fileIORepository.save(fileIOResult));
    }

    @Override
    public FileDTO convert(MultipartFile file, String toFormat, String password) throws Exception {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String nameFile = fileName.substring(0, fileName.lastIndexOf("."));
        String fromFormat = fileName.substring(fileName.lastIndexOf(".") + 1);

        // convert by convertapi
        Config.setDefaultSecret(CONVERT_API_SECRET);
        ConversionResult conversionResult =
                ConvertApi
                        .convert(fromFormat, toFormat,
                                new Param("file", file.getInputStream(), fileName),
                                new Param(toFormat.equals("encrypt") ? "PdfUserPasswordNew" : "Password", password))
                        .get();


        File fileResult = getDataForFileIO(conversionResult, nameFile);

        FileIO fileIOResult = new FileIO();
        fileIOResult.setFilename((toFormat.equals("encrypt") ? "(Encrypted) " : "(Decrypted) ") + fileResult.getName());
        fileIOResult.setContent(Files.readAllBytes(fileResult.toPath()));
        fileIOResult.setEnabled(true);

        System.out.println(fileResult.delete() ? "Đã xóa File converted" : "Xóa file converted thất bại");

        return converter.toDTO(fileIORepository.save(fileIOResult));
    }

}
