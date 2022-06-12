package com.project.kn.scrumsimulator.events;

public class Event extends Card {

    private boolean positive = false;

    public Event(String name, String description, boolean isPositive) {
        super(CardType.EVENT, name, description);
        this.positive = isPositive;
    }

    public boolean isPositive(){
        return positive;
    }
}
