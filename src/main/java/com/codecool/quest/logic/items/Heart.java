package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Heart extends Item {
    public Heart(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "heart";
    }
}
