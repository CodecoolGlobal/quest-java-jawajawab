package com.codecool.quest.logic;

import com.codecool.quest.Main;
import com.codecool.quest.logic.actors.Ghost;
import com.codecool.quest.logic.actors.Giant;
import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;
import com.codecool.quest.logic.items.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 'D':
                            cell.setType(CellType.OPENDOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            map.setSkeleton(new Skeleton(cell));
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            map.setGhost(new Ghost(cell));
                            break;
                        case 'G':
                            cell.setType(CellType.FLOOR);
                            map.setGiant(new Giant(cell));
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
//                            if (Main.playerName.equals("Dan")) {
//                                map.getPlayer().setHealth(99999);
//                            } else if(Main.playerName.equals("Stefan")) {
//                                map.getPlayer().modifyDamage(99999);
//                            }
                            break;
                        case '/':
                            System.out.println("read sword");
                            cell.setType(CellType.FLOOR);
                            map.setSword(new Sword(cell));
                            break;
                        case 'h':
                            System.out.println("read heart");
                            cell.setType(CellType.FLOOR);
                            map.setHeart(new Heart(cell));
                            break;
                        case '%':
                            System.out.println("read doorKey");
                            cell.setType(CellType.FLOOR);
                            map.setDoorKey(new DoorKey(cell));
                            break;
                        case 'd':
                            System.out.println("read door");
                            cell.setType(CellType.FLOOR);
                            map.setDoor(new Door(cell));
                            break;
                        case 'B':
                            System.out.println("read blueDoor");
                            cell.setType(CellType.FLOOR);
                            map.setBlueDoor(new BlueDoor(cell));
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
