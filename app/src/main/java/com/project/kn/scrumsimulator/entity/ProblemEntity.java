package com.project.kn.scrumsimulator.entity;

public class ProblemEntity extends AbstractObject {

    private String name;
    private String description;
    private int groupNumber;
    private int projectId;

    public ProblemEntity(int id, String name, String description, int groupNumber, int projectId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.groupNumber = groupNumber;
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

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
