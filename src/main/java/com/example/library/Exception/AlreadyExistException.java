package com.example.library.Exception;


public class AlreadyExistException extends RuntimeException{

    public AlreadyExistException(String message) {
        super(message);
    }
}
