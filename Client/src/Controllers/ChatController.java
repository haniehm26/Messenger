package Controllers;

import Connection.Connection;
import Connection.InfoHandler;
import Main.Main;
import Moduls.Message;
import Moduls.RequestType;
import Moduls.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;


public class ChatController implements Initializable {
    static String senderUsername;
    private static User senderUser = new User();
    private static User receiverUser = new User();
    private static String receiverUsername;
    @FXML
    Label startLabel;
    @FXML
    JFXButton fileButton;
    @FXML
    JFXButton emojiButton;
    @FXML
    JFXButton sendButton;
    @FXML
    JFXListView chatView;
    @FXML
    JFXListView contactView;
    @FXML
    JFXTextArea chatBox;
    @FXML
    Label userName;
    @FXML
    TextField search;
    private Date date = new Date();

    @FXML
    void fileChooser() {
        FileChooser fileChooser = new FileChooser();
        File selected = fileChooser.showOpenDialog(null);
        if (selected != null)
            chatBox.appendText(String.valueOf(selected));
    }

    @FXML
    void menuBar() {
        Main.setPage("MenuBar");
    }

    @FXML
    public void sendMessage() {
        Message message = new Message();
        message.setSenderUser(senderUser);
        message.setReceiverUser(receiverUser);
        message.setData(chatBox.getText());
        message.setDate(date);
        message.setRequestType(RequestType.CHAT);
        if (chatBox.getText().length() != 0) {
            Connection.getInstance().startSender(message);
            Connection.getInstance().startReceiver();
            Message receivedMessage = Connection.getInstance().message();
            Platform.runLater((() -> handleMessage(receivedMessage)));
            Platform.runLater(this::sendMessage);
        }
        chatBox.clear();
    }

    @FXML
    public void search() {
        for (String users : InfoHandler.contacts) {
            String userName = users.substring(0, users.indexOf("~"));
            String userImage = users.substring(users.indexOf("~") + 1);
            if (userName.toLowerCase().contains(search.getText().toLowerCase()) && !userName.equals(senderUsername)) {
                contactView.getItems().clear();
                loadButtons(loadImage(userImage), userName);
            }
            if ("Saved Messages".toLowerCase().contains(search.getText().toLowerCase())) {
                contactView.getItems().clear();
                loadButtons(loadImage(userImage), "Saved Messages");
            }
            if (search.getText().length() == 0) {
                contactView.getItems().clear();
                contactView.getItems().removeAll();
                loadContacts();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadContacts();
        chatBox.setVisible(false);
        sendButton.setVisible(false);
        emojiButton.setVisible(false);
        fileButton.setVisible(false);
    }

    private ImageView loadImage(String user) {
        Image image = new Image(user);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        return imageView;
    }

    private void loadButtons(ImageView imageView, String user) {
        JFXButton button = new JFXButton();
        button.setText(user);
        button.setGraphic(imageView);
        button.setMaxSize(chatView.getPrefWidth(), chatView.getMaxHeight());
        button.setAlignment(Pos.BASELINE_LEFT);
        button.setOnMouseEntered(event -> button.setCursor(Cursor.HAND));
        button.setOnAction(event -> {
            receiverUsername = button.getText();
            receiverUser.setUsername(receiverUsername);
            receiverUser.setMessages(new ConcurrentHashMap<>());
            userName.setText(receiverUsername + "\n" + "Last Seen: ");
            chatView.getItems().clear();
            chatBox.setVisible(true);
            sendButton.setVisible(true);
            emojiButton.setVisible(true);
            fileButton.setVisible(true);
            startLabel.setVisible(false);
        });
        contactView.getItems().add(button);
    }

    private void loadContacts() {
        User user = new User();
        user.setRequestType(RequestType.CHAT);
        new InfoHandler(user).run();
        senderUsername = InfoHandler.joinedUserName;
        senderUser.setUsername(senderUsername);
        senderUser.setMessages(new ConcurrentHashMap<>());
        if (receiverUsername != null)
            userName.setText("  " + receiverUsername);
        for (String users : InfoHandler.contacts) {
            String userName = users.substring(0, users.indexOf("~"));
            String userImage = users.substring(users.indexOf("~") + 1);
            if (!userName.equals(senderUsername))
                loadButtons(loadImage(userImage), userName);
            else
                loadButtons(loadImage(userImage), "Saved Messages");
        }
    }

    private void handleReceivedMessageRec(Message receivedMessage) {
        Label label = new Label();
        label.setStyle("-fx-background-color: #48D1CC;-fx-background-radius: 10;");
        label.setText("  " + receivedMessage.getData().toString() + "  " + receivedMessage.getDate().getHours() + " : " + receivedMessage.getDate().getMinutes() + "  ");
        label.setPrefSize(label.getText().length() * 5, 40);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        imageView.setImage(new Image(receivedMessage.getSenderUser().getPicture()));
        HBox hBox = new HBox();
        hBox.setTranslateX((chatView.getWidth() - hBox.getPrefWidth()) - (label.getPrefWidth() + (1.5 * imageView.getFitWidth())));
        hBox.getChildren().addAll(label, imageView);
        chatView.getItems().add(hBox);
    }

    private void handleSenderMessageSen(Message receivedMessage) {
        Label label = new Label();
        label.setStyle("-fx-background-color: #DAF7A6;-fx-background-radius: 10;");
        label.setText("  " + receivedMessage.getData().toString() + "  " + receivedMessage.getDate().getHours() + " : " + receivedMessage.getDate().getMinutes() + "  ");
        label.setPrefSize(label.getText().length() * 5, 40);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        imageView.setImage(new Image(receivedMessage.getSenderUser().getPicture()));
        HBox hBox = new HBox();
        hBox.getChildren().addAll(imageView, label);
        chatView.getItems().add(hBox);
    }

    private void handleMessage(Message receivedMessage) {
        if (receivedMessage.getReceiverUser().getUsername().equals(receiverUsername)) {
            handleReceivedMessageRec(receivedMessage);
        }
        if (receivedMessage.getSenderUser().getUsername().equals(senderUsername)) {
            handleSenderMessageSen(receivedMessage);
        }
    }
}
