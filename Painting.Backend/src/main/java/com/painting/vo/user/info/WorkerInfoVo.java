package com.painting.vo.user.info;

import java.io.Serializable;

public class WorkerInfoVo implements Serializable {
    private String username;
    private String email;
    private int credits;
    private double exp;
    private int level;
    private int completedMissionCount;
    private int acceptedMissionCount;
    private int inProgressMissionCount;
    private int abandonedMissionCount;

    public WorkerInfoVo() {
    }

    public WorkerInfoVo(String username, String email, int credits, double exp, int level, int completedMissionCount, int acceptedMissionCount, int inProgressMissionCount, int abandonedMissionCount) {
        this.username = username;
        this.email = email;
        this.credits = credits;
        this.exp = exp;
        this.level = level;
        this.completedMissionCount = completedMissionCount;
        this.acceptedMissionCount = acceptedMissionCount;
        this.inProgressMissionCount = inProgressMissionCount;
        this.abandonedMissionCount = abandonedMissionCount;
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

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCompletedMissionCount() {
        return completedMissionCount;
    }

    public void setCompletedMissionCount(int completedMissionCount) {
        this.completedMissionCount = completedMissionCount;
    }

    public int getAcceptedMissionCount() {
        return acceptedMissionCount;
    }

    public void setAcceptedMissionCount(int acceptedMissionCount) {
        this.acceptedMissionCount = acceptedMissionCount;
    }

    public int getInProgressMissionCount() {
        return inProgressMissionCount;
    }

    public void setInProgressMissionCount(int inProgressMissionCount) {
        this.inProgressMissionCount = inProgressMissionCount;
    }

    public int getAbandonedMissionCount() {
        return abandonedMissionCount;
    }

    public void setAbandonedMissionCount(int abandonedMissionCount) {
        this.abandonedMissionCount = abandonedMissionCount;
    }
}
