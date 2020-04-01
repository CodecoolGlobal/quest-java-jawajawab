package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;
import com.codecool.quest.logic.items.Door;
import com.codecool.quest.logic.items.DoorKey;
import com.codecool.quest.logic.items.Heart;
import com.codecool.quest.logic.items.Sword;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;
    private Skeleton skeleton;
    private Sword sword;
    private Heart heart;
    private DoorKey doorKey;
    private Door door;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setSkeleton(Skeleton skeleton) {
        this.skeleton = skeleton;
    }

    public void setSword(Sword sword) {
        this.sword = sword;
    }

    public void setHeart(Heart heart) {
        this.heart = heart;
    }

    public void setDoorKey(DoorKey doorKey) {
        this.doorKey = doorKey;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public Player getPlayer() {
        return player;
    }

    public Skeleton getSkeleton() {
        return skeleton;
    }

    public Sword getSword() {
        return sword;
    }

    public Heart getHeart() {
        return heart;
    }

    public DoorKey getDoorKey() {
        return doorKey;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
