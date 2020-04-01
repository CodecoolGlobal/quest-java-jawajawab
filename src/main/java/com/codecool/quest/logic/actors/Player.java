package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class Player extends Actor {
    public int damage = 5;
    public int health = 10;
    public Cell cell;
    private static LinkedHashMap<String, Integer> playerInventory = new LinkedHashMap<>();


    public Player(Cell cell) {
        super(cell);

    }

    public String getTileName() {
        return "player";
    }


    public int getDamage() {
        return damage;
    }

    public void modifyHealth(int modifier) {
        this.health -= modifier;
        System.out.println(this.health);
    }

    public int getHealth() {
        return health;
    }

    public void addToPlayerInventory(String itemName, int itemCount) {
        if (playerInventory.containsKey(itemName)) {
            playerInventory.replace(itemName, playerInventory.get(itemName) + itemCount);
        } else {
            playerInventory.put(itemName, itemCount);
        }
    }

    public ObservableList<String> printPlayerInventory() {
        Iterator it = playerInventory.entrySet().iterator();
        ObservableList<String> observableInventory = FXCollections.observableArrayList();
        while(it.hasNext()) {
            String itemAndCount = it.next().toString().replace("=", " X");
            observableInventory.add(itemAndCount);
        }
        return observableInventory;
    }

    public LinkedHashMap<String, Integer> getPlayerInventory() {
        return playerInventory;
    }

    public static boolean openDoor() {
        if (playerInventory.containsKey("doorKey")) return true;
        else return false;
    }

}


