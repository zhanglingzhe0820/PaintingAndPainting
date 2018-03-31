package com.painting.exception.viewexception;

import trapx00.tagx00.response.WrongResponse;

public class MissionAlreadyAcceptedException extends Exception {
    private WrongResponse response = new WrongResponse(10008, "Mission already accepted.");

    public WrongResponse getResponse() {
        return response;
    }
}
