package com.rabin.onlinebookstore.utils;

public class ResponseWrapper {
    private int statusCode;
    private String message;
    private Object response;
    private  boolean success;

    public ResponseWrapper() {
    }

    public ResponseWrapper( boolean success, int statusCode, String message, Object response) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
