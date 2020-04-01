package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Player extends Actor {
    public int damage = 5;
    public int health = 10;
    public Cell cell;

    public Player(Cell cell) {
        super(cell);

    }

    public String getTileName() {
        return "player";
    }

    public int getDamage() {
        return damage;
    }

    public void modifyHealth(int modifier) {
        this.health -= modifier;
        System.out.println(this.health);
    }

    public int getHealth() {
        return health;
    }


}
