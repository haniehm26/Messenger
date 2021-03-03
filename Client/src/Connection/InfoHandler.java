package Connection;


import Moduls.Group;
import Moduls.RequestType;
import Moduls.User;

import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListSet;

public class InfoHandler {

    public static boolean canSignIn;
    public static boolean canRegister;
    public static boolean isPassword;
    public static boolean canEdit;
    public static String getPassword;
    public static String joinedUserName;
    public static String joinedUserImage;
    public static ConcurrentSkipListSet<String> contacts = new ConcurrentSkipListSet<>();
    private User user;
    private Object readInput;
    private Group group;

    public InfoHandler(User user) {
        this.user = user;
    }

    public InfoHandler(Group group) {
        this.group = group;
    }

    public void run() {
        try {
            Connection connection = Connection.getInstance();
            connection.sendToServer(user);
            connection.sendToServer(group);
            readInput = connection.receiveFromServer();
            if (readInput instanceof User) {
                isUser();
            }
            if (readInput instanceof Boolean) {
                canRegister = true;
                System.out.println("Can REGISTER");
                canEdit = true;
                System.out.println("Can Edit");
            }
            if (readInput instanceof String) {
                String password = (String) readInput;
                System.out.println(password);
                isPassword = true;
                getPassword = password;
            }
            if (readInput instanceof ConcurrentSkipListSet) {
                contacts = (ConcurrentSkipListSet) readInput;
            }

        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            System.out.println("Main Closed!");
            e.printStackTrace();
        }
    }

    private void isUser() {
        User user = (User) readInput;
        RequestType requestType = user.getRequestType();
        switch (requestType) {
            case SIGN_IN:
                if (user.getPassword().equals(this.user.getPassword()) && user.getUsername().equals(this.user.getUsername())) {
                    canSignIn = true;
                    System.out.println(this.user.getUsername() + " Joined!");
                    joinedUserName = this.user.getUsername();
                    joinedUserImage = user.getPicture();
                }
                break;
            case NULL:
                isPassword = false;
                canSignIn = false;
                canRegister = false;
                canEdit = false;
                System.out.println("Not Found");
                System.out.println("Can't REGISTER");
                System.out.println("Can't Edit");
                System.out.println("Can't Create Group");
                break;
        }
    }
}
