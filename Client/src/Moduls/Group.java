package Moduls;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class Group extends Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private CopyOnWriteArrayList<User> users;
    private String admin;
    private LinkedBlockingDeque<Message> messages;
    private String name;
    private String image;

    public CopyOnWriteArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(CopyOnWriteArrayList<User> users) {
        this.users = users;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public LinkedBlockingDeque<Message> getMessages() {
        return messages;
    }

    public void setMessages(LinkedBlockingDeque<Message> messages) {
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return getName();
    }
}
