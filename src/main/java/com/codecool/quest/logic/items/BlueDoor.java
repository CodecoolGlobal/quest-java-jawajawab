package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class BlueDoor extends Item {
    public BlueDoor(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "blueDoor";
    }


}
