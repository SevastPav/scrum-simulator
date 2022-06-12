package com.project.kn.scrumsimulator.boardview;

import com.project.kn.scrumsimulator.events.Card;
import com.project.kn.scrumsimulator.events.CardType;
import com.project.kn.scrumsimulator.events.Problem;
import com.project.kn.scrumsimulator.sprint.SprintUtils;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Task extends Card {

    public int id;

    public int priority;

    public int hoursCount;

    private ArrayList<Problem> problems = new ArrayList<>();

    public Task(String name){
        super(CardType.TASK, name, name);
        this.id = -1;
    }

    public Task(String name, String description, int id){
        super(CardType.TASK, name, description);
        this.priority = 1;
        this.hoursCount = 20;
        this.id = id;
    }

    public Task(String name, String description, int priority, int hoursCount, int id){
        super(CardType.TASK, name, description);
        this.priority = priority;
        this.hoursCount = hoursCount;
        this.id = id;
    }

    public String toString(){
        return getName();
    }

    public boolean isBlocked() {

        return !problems.isEmpty();
    }

    public void addProblem(Problem problemCard) {
        problems.add(problemCard);
    }

    public void removeProblem(Problem problemCard) {
        problems.removeIf(p -> p.getId().equals(problemCard.getId()));
    }
}