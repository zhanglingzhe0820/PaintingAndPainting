package com.painting.exception.viewexception;

import trapx00.tagx00.response.WrongResponse;

public class UserAlreadyExistsException extends Exception {
    private WrongResponse response = new WrongResponse(10002, "User already exists.");

    public WrongResponse getResponse() {
        return response;
    }
}