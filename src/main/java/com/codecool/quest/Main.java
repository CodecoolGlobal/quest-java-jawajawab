package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;
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

public class Main extends Application {
    static GameMap map = MapLoader.loadMap();
    static Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label attackLabel = new Label();
//    TableView inventoryDisplayTable = new TableView();
    ListView<String> inventoryItems = new ListView<>();
   static Label itemName = new Label();
    static Button button = new Button();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);

        ui.add(new Label("Attack: "), 0, 1);
        ui.add(attackLabel, 1, 1);

        button.setText("Pick Item");
        button.setVisible(false);


        ui.add(button, 1, 3);
        ui.add(itemName, 0, 3);

        ui.add(new Label("Inventory"), 0, 6);

        String css= "-fx-border-width: 2px; -fx-border-color: lightgrey; -fx-border-radius: 3px; -fx-max-width: 95px;";
        inventoryItems.setStyle(css);

        ui.add(inventoryItems, 0,20);

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
            refresh();
            borderPane.requestFocus();
            System.out.println("Merge");
        });

        inventoryItems.setOnMouseClicked(e -> {
            inventoryItems.getSelectionModel().clearSelection();
            borderPane.requestFocus();
        });
        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
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

        healthLabel.setText("" + map.getPlayer().health);
//        itemName.setText(map.getPlayer().getCell().getTileName());
//         attackLabel.setText(""+ map.getPlayer().getAttackDamage());
        inventoryItems.getSelectionModel().clearSelection();


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
                map.getPlayer().setHealth(5);
                break;
            case "sword":
//                map.getPlayer().setAttackDamage(4);
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

    public static void encounter(Player attacker, Skeleton defender) {
        System.out.println("Encounter " + attacker.damage + " " + defender.damage);
        defender.modifyHealth(attacker.damage);
        attacker.modifyHealth(defender.damage);
        System.out.println("defender health: " + defender.getHealth() + " and attacker health : " + attacker.getHealth());
        if (defender.getHealth() <= 0) {
            defender.getCell().setActor(null);
        }
    }

}
