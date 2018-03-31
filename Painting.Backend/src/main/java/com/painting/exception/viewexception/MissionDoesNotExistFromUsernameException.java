package com.painting.exception.viewexception;

import trapx00.tagx00.response.WrongResponse;

public class MissionDoesNotExistFromUsernameException extends Exception {

    private WrongResponse response = new WrongResponse(10006, "The mission of username does not exist.");

    public WrongResponse getResponse() {
        return response;
    }
}
