package Moduls;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class User extends Request implements Serializable {

    private static long serialVersionUID = 1L;
    private String name;
    private String username;
    private String password;
    private String picture;
    private ConcurrentHashMap<String, LinkedBlockingDeque<Message>> messages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ConcurrentHashMap<String, LinkedBlockingDeque<Message>> getMessages() {
        return messages;
    }

    public void setMessages(ConcurrentHashMap<String, LinkedBlockingDeque<Message>> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return getName() + " " + getPassword() + " " + getUsername() + " " + getPicture() + " ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }
}
