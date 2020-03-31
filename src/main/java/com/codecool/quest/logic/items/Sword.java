package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;

public class Sword extends Item {
    public String tileName;
    public Sword(Cell cell) {
        super(cell);

    }

    public String getTileName() {
        return "sword";
    }

//    public void setTileName() {
//        cell.setType(CellType.FLOOR);
//    }
}
