package com.rabin.onlinebookstore.utils;

public class CustomException extends RuntimeException{

    public CustomException(String errorMessage){
        super(errorMessage);
    }
}
