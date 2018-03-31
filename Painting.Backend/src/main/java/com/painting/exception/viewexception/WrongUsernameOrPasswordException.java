package com.painting.exception.viewexception;

import trapx00.tagx00.response.WrongResponse;

public class WrongUsernameOrPasswordException extends Exception {
    private WrongResponse response = new WrongResponse(10003, "Username or password is wrong.");

    public WrongResponse getResponse() {
        return response;
    }
}
