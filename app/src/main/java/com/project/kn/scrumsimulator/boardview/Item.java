package com.project.kn.scrumsimulator.boardview;

public class Item{

    public String name = "";

    public String description = "";

    public int priority;

    public int hoursCount;

    public Item(String item){
        this.name = item;
    }

    public Item(String item, String description){
        this.name = item;
        this.description = description;
        this.hoursCount = 20;
    }

    public String toString(){
        return name;
    }
}