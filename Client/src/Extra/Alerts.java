package Extra;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Alerts {

    private static Alert alert;

    public static void Error(String Message) {
        alert = new Alert(Alert.AlertType.ERROR, Message);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Alerts.class.getResourceAsStream("../Resources/Images/Error.png")));
        alert.show();
    }

    public static void Warning(String Message) {
        alert = new Alert(Alert.AlertType.WARNING, Message);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Alerts.class.getResourceAsStream("../Resources/Images/Warning.png")));
        alert.show();
    }

    public static void Information(String Message) {
        alert = new Alert(Alert.AlertType.INFORMATION, Message);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Alerts.class.getResourceAsStream("../Resources/Images/Information.png")));
        alert.show();
    }

    public static void Confirmation(String Message) {
        alert = new Alert(Alert.AlertType.CONFIRMATION, Message);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Alerts.class.getResourceAsStream("../Resources/Images/Confirmation.png")));
        alert.show();
    }
}
