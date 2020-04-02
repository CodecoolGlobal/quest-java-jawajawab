package com.codecool.quest;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProfileWindow {

    static String playerName = "";

    public static String displayProfile(String windowTitle, String windowMessage) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(windowTitle);
        window.setMinWidth(300);
        Label label = new Label();
        label.setText(windowMessage);


        TextField name = new TextField("John");
        Button button = new Button("Start!");

        name.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                playerName = name.getText().trim();
                window.close();
            }
        });

        button.setOnAction(e -> {
            playerName = name.getText().trim();
            window.close();
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, name, button);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return playerName;
    }
}
