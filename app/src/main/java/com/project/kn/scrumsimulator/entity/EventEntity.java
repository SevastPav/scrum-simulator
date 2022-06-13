package com.project.kn.scrumsimulator.entity;

public class EventEntity extends AbstractObject {

    private String name;
    private String description;
    private int hoursCount;
    private int projectId;

    public EventEntity(int id, String name, String description, int hoursCount, int projectId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.hoursCount = hoursCount;
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getHoursCount() {
        return hoursCount;
    }

    public void setHoursCount(int hoursCount) {
        this.hoursCount = hoursCount;
    }
}
