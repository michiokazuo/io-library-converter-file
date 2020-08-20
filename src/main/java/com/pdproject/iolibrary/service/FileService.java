package com.pdproject.iolibrary.service;

import com.pdproject.iolibrary.model.FileIO;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface FileService<T> {
    boolean downFiles(List<Integer> listId) throws SQLException;

    boolean shareFiles(List<Integer> listId) throws SQLException;

    FileIO findFile(int id) throws SQLException;

    List<FileIO> findAll(int id_user) throws SQLException;

    List<FileIO> search(String fileName, Date startDate, Date endDate) throws SQLException; // thinking...

    List<FileIO> sortBy(String field, boolean isASC) throws SQLException;

    boolean saveFile(T t) throws SQLException;

    boolean deleteFile(int id) throws SQLException;

    // has account

    T securityFile(int id) throws SQLException;

    T openFile(int id) throws SQLException;

    boolean printFile(int id) throws SQLException;

    T compressFile(int id) throws SQLException;

    T decompressFile(int id) throws SQLException;

    // no account

    T securityFile(T t) throws SQLException;

    T openFile(T t) throws SQLException;

    boolean printFile(T t) throws SQLException;

    T compressFile(T t) throws SQLException;

    T decompressFile(T t) throws SQLException;

}
