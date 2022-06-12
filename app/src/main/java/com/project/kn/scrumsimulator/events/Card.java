package com.project.kn.scrumsimulator.events;

public abstract class Card {

    private CardType cardType;

    private String name;

    private String description;

    public Card(CardType cardType, String name, String description) {
        this.cardType = cardType;
        this.name = name;
        this.description = description;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
