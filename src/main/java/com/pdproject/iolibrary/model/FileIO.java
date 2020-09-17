package com.pdproject.iolibrary.model;

import lombok.Data;

import javax.persistence.*;
import java.io.File;

@Data
@Entity
@Table(name = "file")
public class FileIO extends Base {

    @Column(name = "filename")
    private String filename;

    @Column(name = "content")
    private byte[] content;
}
