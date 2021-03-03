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


public class SignInController {
    @FXML
    JFXTextField username;
    @FXML
    JFXPasswordField password;
    private boolean canSignIn;

    @FXML
    void signIn() {
        checkInfo();
        if (canSignIn) {
            Main.setPage("Chat");
        }
    }

    @FXML
    void forgetPassword() {
        Main.setPage("ForgetPassword");
    }

    @FXML
    void register() {
        Main.setPage("Register");
    }

    private void checkInfo() {
        if (notEmpty()) {
            User user = new User();
            user.setUsername(username.getText());
            user.setPassword(password.getText());
            user.setRequestType(RequestType.SIGN_IN);
            new InfoHandler(user).run();
            if (InfoHandler.canSignIn) {
                canSignIn = true;
            } else Alerts.Error("Username or Password is INCORRECT!");
        } else Alerts.Error("Fields are empty!");
    }

    private boolean notEmpty() {
        return CheckFields.notEmpty(username.getText(), password.getText());
    }
}
