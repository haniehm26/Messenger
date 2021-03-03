package Main;

import Connection.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage Stage;
    private static Object controller;
    private static Parent rootNode;

    public static void main(String[] args) {
        launch(args);
    }

    public static void setPage(String pageName) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../View/" + pageName + ".fxml"));
            rootNode = loader.load();
            controller = loader.getController();
            Stage.setTitle("Telegram");
            Stage.getIcons().add(new Image(Main.class.getResourceAsStream("../Resources/Images/Telegram.png")));
            Stage.setScene(new Scene(rootNode));
            Stage.setResizable(false);
            Stage.show();
        } catch (IOException e) {
            System.out.println("Main/setPage: " + e);
            e.printStackTrace();
        }
    }

    public static void createNewPage(String pageName, String pageTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../View/" + pageName + ".fxml"));
            rootNode = loader.load();
            controller = loader.getController();
            Stage stage = new Stage();
            stage.setTitle(pageTitle);
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("../Resources/Images/Telegram.png")));
            stage.setScene(new Scene(rootNode, 450, 450));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getController() {
        return controller;
    }

    @Override
    public void start(Stage primaryStage) {
        Stage = primaryStage;
        setPage("HomePage");
        Connection connection = Connection.getInstance();
        connection.connectToServer("localhost", 8080);
    }
}
