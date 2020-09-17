package com.pdproject.iolibrary.converter;

public interface Converter<Model, DTO> {
    DTO toDTO(Model model);
    Model toModel(DTO dto);
}
