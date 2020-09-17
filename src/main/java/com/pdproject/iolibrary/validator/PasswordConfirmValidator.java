package com.pdproject.iolibrary.validator;

import com.pdproject.iolibrary.anotation.PasswordConfirm;
import com.pdproject.iolibrary.dto.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmValidator implements ConstraintValidator<PasswordConfirm, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        UserDTO userDTO = (UserDTO) o;
        return userDTO.getPassword().equals(userDTO.getPasswordConfirm());
    }
}
