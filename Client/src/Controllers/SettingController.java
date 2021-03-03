package Controllers;

import Connection.InfoHandler;
import Main.Main;
import Moduls.RequestType;
import Moduls.User;
import Extra.Alerts;
import Extra.CheckFields;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;


public class SettingController {
    public JFXButton logOut;
    public JFXButton edit;
    public JFXButton newImage;
    public ImageView imageView;
    public JFXPasswordField newPassword;
    public JFXTextField newName;
    public JFXTextField newUsername;
    private Image image;

    public void initialize() {
    }

    @FXML
    public void backToMenu() {
        new ChatController();
        Main.setPage("MenuBar");
    }

    @FXML
    public void edit() {
        if (notEmpty()) {
            if (validPasswordUsername()) {
                User user = new User();
                user.setRequestType(RequestType.SETTING);
                user.setUsername(InfoHandler.joinedUserName);
                user.setName(newName.getText());
                user.setPassword(newPassword.getText());
                user.setPicture(imageView.getImage().getUrl());
                new InfoHandler(user).run();
                if (InfoHandler.canEdit) {
                    Alerts.Information("Edited Successfully!");
                } else Alerts.Error("Error in edit!");
            } else
                Alerts.Warning("Notice:\nUsername contains only English letters & numbers!\nPassword must be more than 2 elements!");
        } else Alerts.Error("Fields are empty!");
    }

    public void selectNewImage() {
        FileChooser fileChooser = new FileChooser();
        File selected = fileChooser.showOpenDialog(null);
        if (selected != null)
            image = new Image(selected.getAbsoluteFile().toURI().toString(), 150, 150, true, true);
        imageView.setImage(image);
    }

    public void logOut() {
        Main.setPage("SignIn");
    }

    private boolean notEmpty() {
        return CheckFields.notEmpty(newName.getText(), newPassword.getText());
    }

    private boolean validPasswordUsername() {
        return CheckFields.isPasswordValid(newPassword.getText());
    }
}
