package com.pdproject.iolibrary.converter;

import com.pdproject.iolibrary.dto.FileDTO;
import com.pdproject.iolibrary.model.FileIO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileConverter implements Converter<FileIO, FileDTO> {

    private ModelMapper modelMapper;

    @Override
    public FileDTO toDTO(FileIO fileIO) {
        FileDTO fileDTO = modelMapper.map(fileIO, FileDTO.class);
        fileDTO.setUrlDownload("/api/v1/public/file-download/" + fileIO.getFilename() + "/" + fileIO.getId());
        return fileDTO;
    }

    @Override
    public FileIO toModel(FileDTO fileDTO) {
        return modelMapper.map(fileDTO, FileIO.class);
    }
}
