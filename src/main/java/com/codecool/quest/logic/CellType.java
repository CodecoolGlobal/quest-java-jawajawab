package com.codecool.quest.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    OPENDOOR("opendoor"),
    OPENBLUEDOOR("openBlueDoor"),
    GRAVE("grave"),
    CHARG("charG"),
    CHARA("charA"),
    CHARM("charM"),
    CHARE("charE"),
    CHARO("charO"),
    CHARV("charV"),
    CHARR("charR"),
    CHART("charT"),
    CHARC("charC"),
    CHARL("charL"),
    CHARS("charS");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
