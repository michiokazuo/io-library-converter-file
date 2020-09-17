package com.pdproject.iolibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {

    private String message;
    private Object data;

    public ResponseMessage successResponse(String message, Object data) {
        return new ResponseMessage(message, data);
    }

    public ResponseMessage faildResponse(String message) {
        return new ResponseMessage(message, null);
    }
}
