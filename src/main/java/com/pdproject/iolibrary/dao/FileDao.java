package com.pdproject.iolibrary.dao;

import com.pdproject.iolibrary.model.FileIO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface FileDao<T> {
    List<FileIO> getList(ResultSet resultSet) throws SQLException;

    FileIO getObject(ResultSet resultSet) throws SQLException;

    FileIO saveFile(T t) throws SQLException;

    boolean deleteFile(int id) throws SQLException;

    FileIO findFile(int id) throws SQLException;

    List<FileIO> findAll(int id_user) throws SQLException;

    List<FileIO> search(String fileName, Date startDate, Date endDate) throws SQLException; // thinking...

    List<FileIO> sortBy(String field, boolean isASC) throws SQLException;

}
