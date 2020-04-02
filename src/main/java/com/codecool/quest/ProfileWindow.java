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

/**
 * This is a simple modal window used to display a layout with a textfield and a submit button
 */

public class ProfileWindow {
    /**
     * The name of the player that will be displayed on the right side of the main window
     */

    static String playerName = "";

    /**
     * <p>This method generates a modal window with the title of windowTitle and a label with the message windowMessage</p>
     * @param windowTitle String
     * @param windowMessage String
     * @return playerName String
     */
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

        window.setOnCloseRequest( e-> {
            window.close();
            System.exit(0);
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
