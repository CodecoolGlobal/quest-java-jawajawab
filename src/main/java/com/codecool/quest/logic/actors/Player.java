package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;

public class Player extends Actor {

    private int attackDamage = 1;
    private ObservableMap<String, Integer> playerInventory = FXCollections.observableHashMap();

    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }

    public void setAttackDamage(int attackDamageModifier) {
        attackDamage += attackDamageModifier;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void addToPlayerInventory(String itemName, int itemCount) {

        if (playerInventory.containsKey(itemName)) {
            playerInventory.replace(itemName, playerInventory.get(itemName) + itemCount);
        } else {
            playerInventory.put(itemName, itemCount);
        }

    }

    public String printPlayerInventory() {

        return playerInventory.toString();
    }

    public ObservableMap<String, Integer> getPlayerInventory() {
        return playerInventory;
    }
}


