package Controllers;

import Connection.InfoHandler;
import Moduls.Group;
import Moduls.RequestType;
import Moduls.User;
import Extra.Alerts;
import Extra.CheckFields;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

public class CreateGroup {

    static CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();

    @FXML
    AnchorPane anchorPane;
    @FXML
    JFXButton choose;
    @FXML
    JFXButton submit;
    @FXML
    JFXButton select;
    @FXML
    JFXTextField groupName;
    @FXML
    ImageView imageView;
    @FXML
    JFXListView listView;

    private Image image;
    private boolean selectUsers;
    private String admin = InfoHandler.joinedUserName;

    public void chooseGroupPic() {
        FileChooser fileChooser = new FileChooser();
        File selected = fileChooser.showOpenDialog(null);
        if (selected != null)
            image = new Image(selected.getAbsoluteFile().toURI().toString(), 150, 150, true, true);
        imageView.setImage(image);
    }

    public void submit() {
        if (notEmpty()) {
            if (selectUsers) {
                User admin = new User();
                admin.setUsername(this.admin);
                new InfoHandler(admin);
                if (users.contains(admin)) {
                    Group group = new Group();
                    group.setName(groupName.getText() + " (Group)");
                    group.setImage(imageView.getImage().getUrl());
                    group.setUsers(users);
                    group.setAdmin(this.admin);
                    String groupName = group.getName();
                    String groupImage = group.getImage();
                    new InfoHandler(group).run();
                    InfoHandler.contacts.add(groupName + "~" + groupImage);
                    Alerts.Information("Group Created Successfully!");
                } else Alerts.Error("Select admin!");
            } else Alerts.Warning("Select members!");
        } else Alerts.Error("Fields are empty!");
    }

    public void selectMembers() {
        User user = new User();
        user.setRequestType(RequestType.CHAT);
        new InfoHandler(user).run();
        for (String users : InfoHandler.contacts) {
            String userName = users.substring(0, users.indexOf("~"));
            String userImage = users.substring(users.indexOf("~") + 1);
            if (!users.contains("Group"))
                loadCheckList(loadImage(userImage), userName);
        }
    }

    private void loadCheckList(ImageView imageView, String userName) {
        JFXCheckBox checkBox = new JFXCheckBox();
        checkBox.setText(userName);
        checkBox.setGraphic(imageView);
        checkBox.setCheckedColor(Color.BLACK);
        if (listView.getItems().size() < InfoHandler.contacts.size())
            listView.getItems().addAll(checkBox);
        checkBox.setOnMouseClicked(event -> {
            if (checkBox.isSelected())
                checkBox.setSelected(true);
            else checkBox.setSelected(false);
            if (checkBox.isSelected()) {
                User user = new User();
                user.setUsername(checkBox.getText());
                user.setPicture(checkBox.getGraphic().toString());
                users.add(user);
                System.out.println("added");
            }
            if (!checkBox.isSelected()) {
                User user = new User();
                user.setUsername(checkBox.getText());
                users.remove(user);
                System.out.println("removed");
            }
            if (users.size() != 0) {
                selectUsers = true;
            }
            if (users.size() == 0) {
                selectUsers = false;
            }
        });
    }

    private ImageView loadImage(String user) {
        Image image = new Image(user);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        return imageView;
    }

    private boolean notEmpty() {
        return CheckFields.notEmpty(groupName.getText());
    }

}
