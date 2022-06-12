package com.project.kn.scrumsimulator.events;

import java.io.Serializable;

public abstract class Card implements Serializable {

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
