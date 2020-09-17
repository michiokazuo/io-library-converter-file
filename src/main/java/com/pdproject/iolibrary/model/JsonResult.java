package com.pdproject.iolibrary.model;

import org.springframework.stereotype.Component;

@Component
public class JsonResult {
    private String message;
    private Object data;

    public JsonResult() {
    }

    public JsonResult(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public JsonResult jsonSuccess(Object data) {
        return new JsonResult("Success", data);
    }

    public JsonResult jsonFailure(Object data) {
        return new JsonResult("Failure", data);
    }
}
