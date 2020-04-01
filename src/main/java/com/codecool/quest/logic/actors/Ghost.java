package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Ghost extends Actor {
    public int damage = 3;
    public int health = 10;

    public Ghost(Cell cell) {
        super(cell);

    }

    @Override
    public String getTileName() {
        return "ghost";
    }

    public void modifyHealth(int modifier) {
        this.health -= modifier;
        System.out.println(this.health);
    }

    public int getHealth() {
        return health;
    }

    public void roam() {
        System.out.println("tttttererererere");
//        Cell cell = this.getCell();
//        int dx = getX()+1;
//        int dy = getY()+1;
//        Cell nextCell = cell.getNeighbor(dx, dy);
//        if (verifyValidMove(nextCell)) {
//            cell.setActor(null);
//            nextCell.setActor(this);
////            cell = nextCell;
//        }
    }


}

