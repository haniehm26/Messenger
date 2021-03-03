package Controllers;

import Main.Main;

import javafx.fxml.FXML;


public class HomePageController {
    @FXML
    void startMessaging() {
        Main.setPage("SignIn");
    }
}
