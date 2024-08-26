package com.web.webStoreApp.mainApi.exceptions;

public class UserAlreadyExistsExcpetion extends RuntimeException {

    public UserAlreadyExistsExcpetion(String message) {
        super(message);
    }
}
