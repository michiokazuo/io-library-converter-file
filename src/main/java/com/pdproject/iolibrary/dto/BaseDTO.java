package com.pdproject.iolibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDTO {
    private Integer id;
    private Date createDate;
    private Timestamp modifyDate;
    private Boolean enabled;
}
