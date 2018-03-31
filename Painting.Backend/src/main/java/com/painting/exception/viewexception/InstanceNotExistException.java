package com.painting.exception.viewexception;

import trapx00.tagx00.response.WrongResponse;

public class InstanceNotExistException extends Exception {
    private WrongResponse response = new WrongResponse(10005, "The instance of missionId does not exist.");

    public WrongResponse getResponse() {
        return response;
    }
}
