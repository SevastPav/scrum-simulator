package com.project.kn.scrumsimulator.events;

import com.project.kn.scrumsimulator.entity.ProblemEntity;

import java.util.UUID;

public class Problem extends Card {

    private String id;

    private int group;

    private int taskId;

    public Problem(String name, String description, int group) {
        super(CardType.PROBLEM, name, description);
        this.group = group;
        this.id = UUID.randomUUID().toString();
    }

    public int getGroup() {
        return group;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getId() {
        return id;
    }

    public static Problem fromEntity(ProblemEntity entity) {

        return new Problem(entity.getName(), entity.getDescription(), entity.getGroupNumber());
    }
}
