package com.codecool.quest;

import com.codecool.quest.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();

    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("ghost", new Tile(26, 6));
        tileMap.put("giant", new Tile(30, 8));
        tileMap.put("sword", new Tile(4, 30));
        tileMap.put("heart", new Tile(26, 22));
        tileMap.put("doorKey", new Tile(16, 23));
        tileMap.put("blueDoorKey", new Tile(17, 23));
        tileMap.put("door", new Tile(13, 11));
        tileMap.put("blueDoor", new Tile(1, 9));
        tileMap.put("openBlueDoor", new Tile(2, 9));
        tileMap.put("opendoor", new Tile(12, 11));
        tileMap.put("grave", new Tile(1, 14));
        tileMap.put("charG", new Tile(25, 30));
        tileMap.put("charA", new Tile(19, 30));
        tileMap.put("charM", new Tile(31, 30));
        tileMap.put("charE", new Tile(23, 30));
        tileMap.put("charO", new Tile(20, 31));
        tileMap.put("charV", new Tile(27, 31));
        tileMap.put("charR", new Tile(23, 31));
        tileMap.put("charT", new Tile(25, 31));
        tileMap.put("charC", new Tile(21, 30));
        tileMap.put("charS", new Tile(24, 31));
        tileMap.put("charL", new Tile(30, 30));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
