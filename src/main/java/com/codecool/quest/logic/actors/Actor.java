package com.codecool.quest.logic.actors;

import com.codecool.quest.Main;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (verifyValidMove(nextCell)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
        else {
            if (nextCell.getItem()!=null && nextCell.getItem().getTileName().equals("door")) {
                if (Player.openDoor()) {
                    nextCell.setType(CellType.OPENDOOR);
                    nextCell.setItem(null);
                }
            }
            else if (nextCell.getItem()!=null && nextCell.getItem().getTileName().equals("blueDoor")) {
                if (Player.openBlueDoor()) {
                    nextCell.setType(CellType.OPENBLUEDOOR);
                    nextCell.setItem(null);
                    Main.displayStageClear();
                }
            }
            else {
                Main.encounter(this.getCell(), nextCell);
            }
        }
        if (verifyItem(cell)) {
            Main.showPickupButton();
        } else {
            Main.hidePickupButton();
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health += health;
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

    /**
     * Evaluates the next cell to see if it is not a wall or closed door.
     * @param cell
     * @return true or false
     */
    public static boolean verifyValidMove(Cell cell) {
        if (cell.getTileName().equals("wall") || cell.getActor() != null
                || (cell.getItem()!=null && cell.getItem().getTileName().equals("door"))
                || (cell.getItem()!=null && cell.getItem().getTileName().equals("blueDoor"))) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Evaluates if the player is on top of a pick-able item.
     * @param cell
     * @return true or false
     */
    public boolean verifyItem(Cell cell) {
        if (cell.getItem() != null) {
            return true;
        } else {
            return false;
        }
    }


}
