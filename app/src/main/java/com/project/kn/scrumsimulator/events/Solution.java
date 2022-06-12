package com.project.kn.scrumsimulator.events;

import java.util.UUID;

public class Solution extends Card {

    private String id;

    private int group;

    public Solution(String name, String description, int group) {
        super(CardType.SOLUTION, name, description);
        this.group = group;
        this.id = UUID.randomUUID().toString();
    }

    public int getGroup() {
        return group;
    }

    public String getId() {
        return id;
    }
}
