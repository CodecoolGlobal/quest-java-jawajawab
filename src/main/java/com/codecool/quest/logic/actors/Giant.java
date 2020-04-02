package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Giant extends Actor {
    public int damage = 3;
    public int health = 20;

    public Giant(Cell cell) {
        super(cell);

    }

    @Override
    public String getTileName() {
        return "giant";
    }

    public void modifyHealth(int modifier) {
        this.health -= modifier;
        System.out.println(this.health);
    }

    public int getHealth() {
        return health;
    }

}

