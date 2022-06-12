package com.project.kn.scrumsimulator.boardview;

import com.project.kn.scrumsimulator.events.Card;
import com.project.kn.scrumsimulator.events.CardType;

public class Task extends Card {

    public int priority;

    public int hoursCount;

    public Task(String name){
        super(CardType.TASK, name, name);
    }

    public Task(String name, String description){
        super(CardType.TASK, name, description);
        this.priority = 1;
        this.hoursCount = 20;
    }

    public Task(String name, String description, int priority, int hoursCount){
        super(CardType.TASK, name, description);
        this.priority = priority;
        this.hoursCount = hoursCount;
    }

    public String toString(){
        return getName();
    }
}