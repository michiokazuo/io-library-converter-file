package com.pdproject.iolibrary.service;

import java.sql.SQLException;

public interface ConvertFileService<T> {
    // use when after signed in
    String getFormatFile(int id) throws SQLException;

    T allToPDF(int id) throws SQLException;

    T PDFToAll(int id) throws SQLException;

    // use when no account or use file immediately after uploading
    String getFormatFile(T t) throws SQLException;

    T allToPDF(T t) throws SQLException;

    T PDFToAll(T t) throws SQLException;
}
