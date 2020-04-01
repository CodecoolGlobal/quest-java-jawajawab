package com.codecool.quest.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    OPENDOOR("opendoor"),
    GRAVE("grave"),
    CHARG("charG"),
    CHARA("charA"),
    CHARM("charM"),
    CHARE("charE"),
    CHARO("charO"),
    CHARV("charV"),
    CHARR("charR");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
