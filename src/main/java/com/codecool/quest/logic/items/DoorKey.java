package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class DoorKey extends Item{
    public DoorKey(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "doorKey";
    }
}
