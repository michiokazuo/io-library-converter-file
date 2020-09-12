package com.pdproject.iolibrary.model;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name = "file")
public class FileIO extends Base {

    @Column(name = "filename")
    private String filename;

    @Column(name = "content")
    private byte[] content;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
