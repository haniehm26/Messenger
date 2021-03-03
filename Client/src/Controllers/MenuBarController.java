package Controllers;

import Connection.InfoHandler;
import Main.Main;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;


public class MenuBarController implements Initializable {
    @FXML
    JFXButton userImage;

    @FXML
    public void backToChat() {
        Main.setPage("Chat");
    }

    @FXML
    public void setting() {
        Main.setPage("Setting");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(InfoHandler.joinedUserImage);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        userImage.setTextFill(Color.WHITE);
        userImage.setText("   " + ChatController.senderUsername);
        userImage.setGraphic(imageView);
        userImage.setPrefHeight(70);
        userImage.setPrefWidth(70 + (userImage.getText().length()) * 10);
    }

    @FXML
    public void newChannel() {
        Main.createNewPage("CreateChannel", "New Channel");
    }

    @FXML
    public void newGroup() {
        Main.createNewPage("CreateGroup", "New Group");
    }
}
