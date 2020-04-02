package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Skeleton extends Actor {
    public int damage = 2;
    public int health = 10;
    public int moveCounter = 0;
    public boolean canMove = true;

    public Skeleton(Cell cell) {
        super(cell);

    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    public void modifyHealth(int modifier) {
        this.health -= modifier;
        System.out.println(this.health);
    }

    public int getHealth() {
        return health;
    }


}
