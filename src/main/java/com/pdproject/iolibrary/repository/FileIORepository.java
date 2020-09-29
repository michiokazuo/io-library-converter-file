package com.pdproject.iolibrary.repository;

import com.pdproject.iolibrary.model.FileIO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileIORepository extends JpaRepository<FileIO, Integer> {

    FileIO findByIdAndEnabledIsTrue(int id);

    FileIO findByOrderByCreateDateDesc();
}
