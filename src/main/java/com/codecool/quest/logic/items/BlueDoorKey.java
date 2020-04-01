package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class BlueDoorKey extends Item{
    public BlueDoorKey(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "blueDoorKey";
    }
}
