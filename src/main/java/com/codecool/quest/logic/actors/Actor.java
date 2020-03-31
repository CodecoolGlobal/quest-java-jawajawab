package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.Main;
import javafx.scene.control.Button;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        System.out.println("Enter a new move");
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (verifyValidMove(nextCell)) {

            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
        if (verifyItem(cell)) {
//            cell.setItem(null);

            Main.buttonVis();
            System.out.println("Button displayed");
//            cell.setActor(null);
//            nextCell.setActor(this);
//            cell = nextCell;
        } else {
            Main.buttonDisappear();
        }
        System.out.println("After if");


    }

    public int getHealth() {
        return health;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public boolean verifyValidMove(Cell cell) {
        if (cell.getTileName().equals("wall") || cell.getActor()!=null) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean verifyItem(Cell cell) {
        if (cell.getItem() != null) {
            System.out.println("Is item");
            return true;
        }
        else {
            System.out.println("Is not item");
            return false;
        }
    }
}
