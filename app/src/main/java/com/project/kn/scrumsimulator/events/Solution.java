package com.project.kn.scrumsimulator.events;

public class Solution extends Card {

    private int group;

    public Solution(String name, String description, int group) {
        super(CardType.SOLUTION, name, description);
        this.group = group;
    }

    public int getGroup() {
        return group;
    }
}
