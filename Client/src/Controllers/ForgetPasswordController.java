package Controllers;


import Connection.InfoHandler;
import Main.Main;
import Moduls.RequestType;
import Moduls.User;
import Extra.Alerts;
import Extra.CheckFields;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ForgetPasswordController implements Initializable {
    @FXML
    JFXTextField username;
    @FXML
    JFXTextField name;
    @FXML
    JFXTextField numbers;
    @FXML
    JFXTextField security;

    private boolean foundPassword;

    @FXML
    void submit() {
        checkInfo();
        if (foundPassword)
            Alerts.Information("Your Password is: " + InfoHandler.getPassword);
    }

    @FXML
    void backToSignIn() {
        Main.setPage("SignIn");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String number = String.valueOf(makeRandom());
        security.setText(number);
    }

    private int makeRandom() {
        Random rand = new Random();
        return rand.nextInt(1000) + 999;
    }

    private void checkInfo(){
        if (notEmpty()) {
            User user = new User();
            user.setName(name.getText());
            user.setUsername(username.getText());
            user.setRequestType(RequestType.FORGOT_PASSWORD);
            new InfoHandler(user).run();
            if (correctNumber() && InfoHandler.isPassword) {
                foundPassword = true;

            } else {
                Alerts.Error("Entered information is INCORRECT!");
                foundPassword = false;
            }
        } else Alerts.Error("Fields are empty!");
    }

    private boolean notEmpty() {
        return CheckFields.notEmpty(username.getText(), name.getText(), numbers.getText());
    }

    private boolean correctNumber() {
        return numbers.getText().equals(security.getText());
    }
}
