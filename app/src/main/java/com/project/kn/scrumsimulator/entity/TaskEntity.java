package com.project.kn.scrumsimulator.entity;

public class TaskEntity extends AbstractObject {

    private String name;
    private String description;
    private int hours;
    private int priority;
    private int projectId;

    public TaskEntity(int id, String name, String description, int hours, int priority, int projectId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.hours = hours;
        this.priority = priority;
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

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
