package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.*;
import com.codecool.quest.logic.items.BlueDoorKey;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    static GameMap map = MapLoader.loadMap();
    static Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label attackLabel = new Label();
    ListView<String> inventoryItems = new ListView<>();
    static Label itemName = new Label();
    static Button button = new Button();
    String playerHealthValue = "";
    String playerAttackValue = "";
    public static String playerName;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        playerName = ProfileWindow.displayProfile("Start your Adventure", "Welcome adventurer, please state your name!");
        cheat();
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        playerHealthValue = Integer.toString(map.getPlayer().health);
        playerAttackValue = Integer.toString(map.getPlayer().damage);

        ui.add(new Label("Adventurer: "+ playerName), 0,0, 3 ,1 );

        healthLabel= new Label("Health: " + playerHealthValue );
        ui.add(healthLabel, 0, 1);

      attackLabel =  new Label("Attack: " + playerAttackValue);
      ui.add(attackLabel, 0, 2);

        button.setText("Pick Item");
        button.setVisible(false);

        ui.add(button, 1, 4);
        ui.add(itemName, 0, 4);

        ui.add(new Label("Inventory"), 0, 7);

        String css= "-fx-border-width: 2px; -fx-border-color: lightgrey; -fx-border-radius: 3px; -fx-max-width: 100px;";
        inventoryItems.setStyle(css);

        ui.add(inventoryItems, 0,20, 3, 1);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        borderPane.requestFocus();
        button.setOnMouseClicked(e -> {
            handlePickup();
            inventoryItems.getSelectionModel().clearSelection();
            borderPane.requestFocus();
            System.out.println("Merge");

        });

        inventoryItems.setOnMouseClicked(e -> {
            inventoryItems.getSelectionModel().clearSelection();
            borderPane.requestFocus();
        });
        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> closeProgram(primaryStage));

    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                moveMonster();
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                moveMonster();
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                moveMonster();
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                moveMonster();
                refresh();
                break;
        }
    }

    private void refresh() {

        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }

        playerHealthValue = Integer.toString(map.getPlayer().health);
        playerAttackValue = Integer.toString(map.getPlayer().damage);
        inventoryItems.getSelectionModel().clearSelection();
        healthLabel.setText("Health: "+playerHealthValue);
        attackLabel.setText("Attack: "+playerAttackValue);

    }


    public static void buttonVis() {
        String item = map.getPlayer().getCell().getItem().getTileName();
        itemName.setText(item);
        button.setVisible(true);
    }

    public static void buttonDisappear() {
        button.setVisible(false);
        itemName.setText("");

    }

    public void handlePickup() {
        itemName.setText("");
        String item = map.getPlayer().getCell().getItem().getTileName();
        switch(item) {
            case "heart":
                map.getPlayer().modifyHealth(-10);
                healthLabel.setText("Health: " + map.getPlayer().health);
                break;
            case "sword":
                map.getPlayer().modifyDamage(4);
                attackLabel.setText("Attack: "+ map.getPlayer().damage);
                break;
            default:
                break;
        }
        map.getPlayer().addToPlayerInventory(item, 1);
        map.getPlayer().getCell().setItem(null);
        System.out.println(map.getPlayer().printPlayerInventory());
        inventoryItems.setItems(map.getPlayer().printPlayerInventory());
        inventoryItems.getSelectionModel().clearSelection();
        buttonDisappear();
    }

    public static void encounter(Cell cell, Cell nextCell) {
        Player attacker = (Player) cell.getActor();
        if (nextCell.getActor().getTileName().equals("skeleton")) {
            ((Skeleton) nextCell.getActor()).canMove = false;
            ((Skeleton) nextCell.getActor()).modifyHealth(attacker.damage);
            if (((Skeleton) nextCell.getActor()).health <= 0) {
                nextCell.setActor(null);
            } else attacker.modifyHealth(((Skeleton) nextCell.getActor()).damage);
            if (attacker.getHealth() <= 0) {
                attacker.getCell().setType(CellType.GRAVE);
                attacker.getCell().setActor(null);
                displayGameOver();
            }
        }
        else if (nextCell.getActor().getTileName().equals("ghost")) {
            ((Ghost) nextCell.getActor()).canMove = false;
            ((Ghost) nextCell.getActor()).modifyHealth(attacker.damage);
            if (((Ghost) nextCell.getActor()).health <= 0) {
                nextCell.setActor(null);
            } else attacker.modifyHealth(((Ghost) nextCell.getActor()).damage);
            if (attacker.getHealth() <= 0) {
                attacker.getCell().setType(CellType.GRAVE);
                attacker.getCell().setActor(null);
                displayGameOver();
            }
        }
        else if (nextCell.getActor().getTileName().equals("giant")) {
            Giant defender = (Giant) nextCell.getActor();
            defender.modifyHealth(attacker.damage);
            if (defender.getHealth() <= 0) {
                defender.getCell().setActor(null);
                map.setBlueDoorKey(new BlueDoorKey(defender.getCell()));
            } else attacker.modifyHealth(defender.damage);
            if (attacker.getHealth() <= 0) {
                attacker.getCell().setType(CellType.GRAVE);
                attacker.getCell().setActor(null);
                displayGameOver();
            }
        }
    }

    public static void displayGameOver() {
        Cell cell = map.getCell(12, 10);
        cell.setType(CellType.CHARG);
        cell = map.getCell(13, 10);
        cell.setType(CellType.CHARA);
        cell = map.getCell(14, 10);
        cell.setType(CellType.CHARM);
        cell = map.getCell(15, 10);
        cell.setType(CellType.CHARE);
        cell = map.getCell(12, 11);
        cell.setType(CellType.CHARO);
        cell = map.getCell(13, 11);
        cell.setType(CellType.CHARV);
        cell = map.getCell(14, 11);
        cell.setType(CellType.CHARE);
        cell = map.getCell(15, 11);
        cell.setType(CellType.CHARR);

    }

    public static void displayStageClear() {
        Cell cell = map.getCell(11, 10);
        cell.setType(CellType.CHARS);
        cell = map.getCell(12, 10);
        cell.setType(CellType.CHART);
        cell = map.getCell(13, 10);
        cell.setType(CellType.CHARA);
        cell = map.getCell(14, 10);
        cell.setType(CellType.CHARG);
        cell = map.getCell(15, 10);
        cell.setType(CellType.CHARE);
        cell = map.getCell(11, 11);
        cell.setType(CellType.CHARC);
        cell = map.getCell(12, 11);
        cell.setType(CellType.CHARL);
        cell = map.getCell(13, 11);
        cell.setType(CellType.CHARE);
        cell = map.getCell(14, 11);
        cell.setType(CellType.CHARA);
        cell = map.getCell(15, 11);
        cell.setType(CellType.CHARR);
    }

    public void moveMonster() {
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    moveMonsters(cell, cell.getActor().getTileName());
                }
            }
        }
    }

    public void moveMonsters(Cell cell, String monster) {
        System.out.println("move monsters started. moving " + cell.getActor() + " from x:" + cell.getX() + " y:" + cell.getY());
        List<Integer> randomChoice = new ArrayList<>();
        randomChoice.add(-1);
        randomChoice.add(0);
        randomChoice.add(1);
        Random r = new Random();
        int dx = randomChoice.get(r.nextInt(randomChoice.size()));
        int dy = randomChoice.get(r.nextInt(randomChoice.size()));;
        if (monster.equals("skeleton")) {
            Cell nextCell = cell.getNeighbor(dx, dy);
            System.out.println("nextCell set to: " + nextCell.getX() + " " + nextCell.getY());
            if (Actor.verifyValidMove(nextCell)) {
                System.out.println("move validated. moving " + (Skeleton) cell.getActor() + " to: x:" + nextCell.getX() + " y:" + nextCell.getY());
                Skeleton mover = (Skeleton) cell.getActor();
                mover.moveCounter++;
                System.out.println("current moves " + mover.moveCounter);
                if (mover.canMove && mover.moveCounter%7==0) {
                    cell.setActor(null);
                    nextCell.setActor(mover);
                }
            }
        }
        else if (monster.equals("ghost")) {
            Cell nextCell = cell.getNeighbor(dx, dy);
            System.out.println("nextCell set to: " + nextCell.getX() + " " + nextCell.getY());
            if (Actor.verifyValidMove(nextCell)) {
                System.out.println("move validated. moving " + (Ghost) cell.getActor() + " to: x:" + nextCell.getX() + " y:" + nextCell.getY());
                Ghost mover = (Ghost) cell.getActor();
                mover.moveCounter++;
                System.out.println("current moves " + mover.moveCounter);
                if (mover.canMove && mover.moveCounter%5==0) {
                    cell.setActor(null);
                    nextCell.setActor(mover);
                }
            }
        }
        else System.out.println("monster not sk or gh");

    }

    public void cheat() {
        if (playerName.trim().equals("Dan")) {
            map.getPlayer().modifyHealth(-9989);
        } else if(playerName.trim().equals("Stefan")) {
            map.getPlayer().modifyDamage(9998);
        } else if(playerName.trim().equals("Jesus")) {
            map.getPlayer().modifyHealth(-9989);
            map.getPlayer().modifyDamage(9998);
        } else if(playerName.trim().equals("")) {
            playerName = "Incognito";
        }
    }
    private void closeProgram(Stage stage) {
        stage.close();
        System.exit(0);
    }
}
