package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.items.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import javax.swing.text.html.ListView;
import java.lang.reflect.Array;
import java.util.*;

public class Player extends Actor {

    private int attackDamage = 1;
    private LinkedHashMap<String, Integer> playerInventory = new LinkedHashMap<>();

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
}


