package com.project.kn.scrumsimulator.entity;

import java.util.Date;

public class GameEntity extends AbstractObject {

    private int playerId;
    private int projectId;
    private Date createdAt;
    private int totalHoursHount;
    private int maxHoursCount;

    public GameEntity(int id, int playerId, int projectId, Date createdAt, int totalHoursHount, int maxHoursCount) {
        this.id = id;
        this.playerId = playerId;
        this.projectId = projectId;
        this.createdAt = createdAt;
        this.totalHoursHount = totalHoursHount;
        this.maxHoursCount = maxHoursCount;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getTotalHoursHount() {
        return totalHoursHount;
    }

    public void setTotalHoursHount(int totalHoursHount) {
        this.totalHoursHount = totalHoursHount;
    }

    public int getMaxHoursCount() {
        return maxHoursCount;
    }

    public void setMaxHoursCount(int maxHoursCount) {
        this.maxHoursCount = maxHoursCount;
    }
}
