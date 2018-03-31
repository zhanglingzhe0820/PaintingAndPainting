package com.painting.exception.viewexception;

import trapx00.tagx00.response.WrongResponse;

public class NotMissionException extends Exception {
    private WrongResponse response = new WrongResponse(10007, "Mission does not exist.");

    public WrongResponse getResponse() {
        return response;
    }
}
