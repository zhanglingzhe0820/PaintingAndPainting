package com.painting.response.user;

import trapx00.tagx00.response.Response;

public class AdminInfoResponse extends Response {
    private int userCount;
    private int totalMissionCount;
    private int totalInstanceCount;
    private int inProgressInstanceCount;
    private int submittedInstanceCount;
    private int finalizeInstanceCount;

    public AdminInfoResponse() {
    }

    public AdminInfoResponse(int userCount, int totalMissionCount, int totalInstanceCount, int inProgressInstanceCount, int submittedInstanceCount, int finalizeInstanceCount) {
        this.userCount = userCount;
        this.totalMissionCount = totalMissionCount;
        this.totalInstanceCount = totalInstanceCount;
        this.inProgressInstanceCount = inProgressInstanceCount;
        this.submittedInstanceCount = submittedInstanceCount;
        this.finalizeInstanceCount = finalizeInstanceCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getTotalMissionCount() {
        return totalMissionCount;
    }

    public void setTotalMissionCount(int totalMissionCount) {
        this.totalMissionCount = totalMissionCount;
    }

    public int getTotalInstanceCount() {
        return totalInstanceCount;
    }

    public void setTotalInstanceCount(int totalInstanceCount) {
        this.totalInstanceCount = totalInstanceCount;
    }

    public int getInProgressInstanceCount() {
        return inProgressInstanceCount;
    }

    public void setInProgressInstanceCount(int inProgressInstanceCount) {
        this.inProgressInstanceCount = inProgressInstanceCount;
    }

    public int getSubmittedInstanceCount() {
        return submittedInstanceCount;
    }

    public void setSubmittedInstanceCount(int submittedInstanceCount) {
        this.submittedInstanceCount = submittedInstanceCount;
    }

    public int getFinalizeInstanceCount() {
        return finalizeInstanceCount;
    }

    public void setFinalizeInstanceCount(int finalizeInstanceCount) {
        this.finalizeInstanceCount = finalizeInstanceCount;
    }
}
