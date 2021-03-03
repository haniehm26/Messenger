package Controllers;

import Connection.InfoHandler;
import Main.Main;
import Moduls.RequestType;
import Moduls.User;
import Extra.Alerts;
import Extra.CheckFields;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class RegisterController {
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private ImageView imageView;
    @FXML
    private Image image;

    private boolean canRegister;

    @FXML
    void submit() throws InterruptedException {
        checkInfo();
        if (canRegister) {
            Alerts.Information("Registration was successful!");
            Thread.sleep(1000);
            Main.setPage("SignIn");
        }
    }

    @FXML
    void setPicture() {
        FileChooser fileChooser = new FileChooser();
        File selected = fileChooser.showOpenDialog(null);
        if (selected != null)
            image = new Image(selected.getAbsoluteFile().toURI().toString(), 150, 150, true, true);
        imageView.setImage(image);
    }

    @FXML
    void backToSignIn() {
        Main.setPage("SignIn");
    }

    private void checkInfo(){
        if (notEmpty()) {
            if (validPasswordUsername()) {
                User user = new User();
                user.setName(name.getText());
                user.setUsername(username.getText());
                user.setPassword(password.getText());
                System.out.println(image.toString());
                user.setPicture(image.getUrl());
                user.setRequestType(RequestType.REGISTER);
                new InfoHandler(user).run();
                if (InfoHandler.canRegister) {
                    canRegister = true;
                } else Alerts.Error("This username has been used before!");
            } else
                Alerts.Warning("Notice:\nUsername contains only English letters & numbers!\nPassword must be more than 2 elements!");
        } else Alerts.Error("Fields are empty!");
    }

    private boolean notEmpty() {
        return CheckFields.notEmpty(name.getText(), username.getText(), password.getText(),image.getUrl());
    }

    private boolean validPasswordUsername() {
        return CheckFields.isUsernameValid(username.getText()) && CheckFields.isPasswordValid(password.getText());
    }
}
