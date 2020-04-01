package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;

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

        button.setText("Pick up Item");
        button.setVisible(false);


        ui.add(button, 1, 3);
        ui.add(itemName, 0, 3);

        ui.add(new Label("Inventory"), 0, 6);


//        TableColumn nameColumn = new TableColumn("Name");
//        TableColumn countColumn = new TableColumn("Count");
//
//
//        nameColumn.setReorderable(false);
//        nameColumn.setResizable(false);
//        nameColumn.setSortable(false);
//
//        countColumn.setReorderable(false);
//        countColumn.setResizable(false);
//        countColumn.setSortable(false);
//
//        inventoryDisplayTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        inventoryDisplayTable.getColumns().addAll(nameColumn, countColumn);
//        inventoryDisplayTable.setEditable(false);
//        Label tablePlaceholder = new Label("Inventory is empty");
//        inventoryDisplayTable.setPlaceholder(tablePlaceholder);
//
//

        ui.add(inventoryItems, 0,8);

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
        healthLabel.setText("" + map.getPlayer().getHealth());
        attackLabel.setText(""+ map.getPlayer().getAttackDamage());


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
                map.getPlayer().setAttackDamage(4);
                break;
            default:
                break;
        }
        map.getPlayer().addToPlayerInventory(item, 1);
        map.getPlayer().getCell().setItem(null);
        System.out.println(map.getPlayer().printPlayerInventory());
        inventoryItems.setItems(map.getPlayer().printPlayerInventory());
        buttonDisappear();
    }

}
