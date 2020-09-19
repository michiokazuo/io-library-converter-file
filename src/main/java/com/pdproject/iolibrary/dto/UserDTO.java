package com.pdproject.iolibrary.dto;

import com.pdproject.iolibrary.anotation.PasswordConfirm;
import com.pdproject.iolibrary.anotation.ValidEmail;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordConfirm
public class UserDTO extends BaseDTO {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NotEmpty
    @NotNull
    @Length(min = 8, max = 32)
    private String password;

    @NotEmpty
    @NotNull
    @Length(min = 8, max = 32)
    private String passwordConfirm;

    private String avatar;

    private List<FileDTO> fileDTOList;
}
