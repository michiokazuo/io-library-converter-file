package com.pdproject.iolibrary.converter;

public interface Converter<Model, DTO> {
    DTO toDTO(Model model) throws Exception;
    Model toModel(DTO dto) throws Exception;
}
