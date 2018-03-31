package com.painting.exception.viewexception;

import trapx00.tagx00.response.WrongResponse;

public class MissionIdDoesNotExistException extends Exception {
    private WrongResponse response = new WrongResponse(10004, "Mission id does not exist.");

    public WrongResponse getResponse() {
        return response;
    }
}
