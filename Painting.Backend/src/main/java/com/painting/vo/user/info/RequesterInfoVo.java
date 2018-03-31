package com.painting.vo.user.info;

import java.io.Serializable;

public class RequesterInfoVo implements Serializable {
    private String username;
    private String email;
    private int submittedMissionCount;
    private int instanceCount;
    private int awaitingCommentInstanceCount;
    private int inProgressInstanceCount;
    private int finalizedInstanceCount;

    public RequesterInfoVo() {
    }

    public RequesterInfoVo(String username, String email, int submittedMissionCount, int instanceCount, int awaitingCommentInstanceCount, int inProgressInstanceCount, int finalizedInstanceCount) {
        this.username = username;
        this.email = email;
        this.submittedMissionCount = submittedMissionCount;
        this.instanceCount = instanceCount;
        this.awaitingCommentInstanceCount = awaitingCommentInstanceCount;
        this.inProgressInstanceCount = inProgressInstanceCount;
        this.finalizedInstanceCount = finalizedInstanceCount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSubmittedMissionCount() {
        return submittedMissionCount;
    }

    public void setSubmittedMissionCount(int submittedMissionCount) {
        this.submittedMissionCount = submittedMissionCount;
    }

    public int getInstanceCount() {
        return instanceCount;
    }

    public void setInstanceCount(int instanceCount) {
        this.instanceCount = instanceCount;
    }

    public int getAwaitingCommentInstanceCount() {
        return awaitingCommentInstanceCount;
    }

    public void setAwaitingCommentInstanceCount(int awaitingCommentInstanceCount) {
        this.awaitingCommentInstanceCount = awaitingCommentInstanceCount;
    }

    public int getInProgressInstanceCount() {
        return inProgressInstanceCount;
    }

    public void setInProgressInstanceCount(int inProgressInstanceCount) {
        this.inProgressInstanceCount = inProgressInstanceCount;
    }

    public int getFinalizedInstanceCount() {
        return finalizedInstanceCount;
    }

    public void setFinalizedInstanceCount(int finalizedInstanceCount) {
        this.finalizedInstanceCount = finalizedInstanceCount;
    }
}
