package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;

public class Player extends Actor {

    private int attackDamage = 1;
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
}
