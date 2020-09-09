package com.pdproject.iolibrary.model;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name = "file")
public class FileIO extends Base {

    @Column(name = "filename")
    private String filename;

    @Column(name = "content")
    private File content;

    @ManyToOne
    @JoinColumn(name = "create_by", referencedColumnName = "id")
    private User user;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public File getContent() {
        return content;
    }

    public void setContent(File content) {
        this.content = content;
    }
}
