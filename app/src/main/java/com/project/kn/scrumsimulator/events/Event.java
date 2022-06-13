package com.project.kn.scrumsimulator.events;

import com.project.kn.scrumsimulator.entity.EventEntity;

public class Event extends Card {

    private boolean positive = false;
    private int hoursCount;

    public Event(String name, String description, int hoursCount) {
        super(CardType.EVENT, name, description);
        this.positive = hoursCount >= 0;
        this.hoursCount = hoursCount;
    }

    public boolean isPositive(){
        return positive;
    }

    public static Event fromEntity(EventEntity entity) {

        return new Event(entity.getName(), entity.getDescription(), entity.getHoursCount());
    }
}
