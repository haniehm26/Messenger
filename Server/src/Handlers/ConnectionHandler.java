package Handlers;


import Moduls.Group;
import Moduls.Message;
import Moduls.RequestType;
import Moduls.User;
import Server.Server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionHandler implements Runnable {

    private final String userFileName = "D:\\Works\\Java Projects\\Messenger\\Server\\src\\Files\\Users.ser";
    private final String groupFileName = "D:\\Works\\Java Projects\\Messenger\\Server\\src\\Files\\Groups.ser";
    private ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Group> groupMap = new ConcurrentHashMap<>();
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private FileHandler fileHandler = new FileHandler();
    private Object readInput;
    private User user;
    private Group group;


    {
        try {
            System.out.println(Server.contacts);
            userMap = (ConcurrentHashMap<String, User>) fileHandler.readObjectFromFile(userFileName);
            groupMap = (ConcurrentHashMap<String, Group>) fileHandler.readObjectFromFile(groupFileName);
            Group group = null;
            for (String key : groupMap.keySet()) {
                group = groupMap.get(key);
            }
            assert group != null;
            String groups = group.getName() + "~" + group.getImage();
            Server.contacts.add(groups);
        } catch (RuntimeException e) {
            Path path = Paths.get(userFileName);
            Path path1 = Paths.get(groupFileName);
            File filePath = path.toFile();
            File filePath1 = path1.toFile();
            if (!filePath.exists()) {
                try {
                    Files.createFile(path);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (!filePath1.exists()) {
                try {
                    Files.createFile(path1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public ConnectionHandler(Socket socket) throws IOException {
        this.socket = socket;
        input = new ObjectInputStream(socket.getInputStream());
        output = new ObjectOutputStream(socket.getOutputStream());
    }

    public ConnectionHandler() {
    }

    @Override
    public void run() {
        try {
            while (true) {
                readInput = input.readObject();
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                if (readInput instanceof User) {
                    user = (User) readInput;
                    RequestType requestType = user.getRequestType();
                    switch (requestType) {
                        case REGISTER:
                            if (checkDuplicateUsername(user.getUsername())) {
                                userMap.put(user.getUsername(), user);
                                fileHandler.writeObjectToFile(userMap, userFileName);
                                String users = userMap.get(user.getUsername()).getUsername() +
                                        "~" + userMap.get(user.getUsername()).getPicture();
                                Server.contacts.add(users);
                                user.setRequestType(RequestType.REGISTER);
                                output.writeObject(true);
                                new ConnectionHandler();
                            } else {
                                System.out.println("Duplicate Username!");
                                user.setRequestType(RequestType.NULL);
                                output.writeObject(user);
                            }
                            break;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        case SETTING:
                            if (userMap.containsKey(user.getUsername())) {
                                Server.contacts.clear();
                                userMap.put(user.getUsername(), user);
                                fileHandler.writeObjectToFile(userMap, userFileName);
                                for (String userName : userMap.keySet()) {
                                    String users = userMap.get(userName).getUsername() + "~" + userMap.get(userName).getPicture();
                                    Server.contacts.add(users);
                                }
                                user.setRequestType(RequestType.SETTING);
                                output.writeObject(false);
                                System.out.println("Edited Successfully!");
                                new ConnectionHandler();
                            } else {
                                user.setRequestType(RequestType.NULL);
                                output.writeObject(user);
                                System.out.println("Can't Edit!");
                            }
                            break;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        case SIGN_IN:
                            for (String userName : userMap.keySet()) {
                                String users = userMap.get(userName).getUsername() + "~" + userMap.get(userName).getPicture();
                                Server.contacts.add(users);
                            }
                            if (foundUser(user.getUsername(), user.getPassword())) {
                                user.setRequestType(RequestType.SIGN_IN);
                                userMap.get(user.getUsername()).setRequestType(RequestType.SIGN_IN);
                                output.writeObject(userMap.get(user.getUsername()));
                                Server.sockets.put(user.getUsername(), this);
                            } else {
                                user.setRequestType(RequestType.NULL);
                                output.writeObject(user);
                            }
                            break;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        case FORGOT_PASSWORD:
                            if (foundPassword(user.getUsername(), user.getName())) {
                                user.setRequestType(RequestType.FORGOT_PASSWORD);
                                userMap.get(user.getUsername()).setRequestType(RequestType.FORGOT_PASSWORD);
                                output.writeObject(userMap.get(user.getUsername()).getPassword());
                            } else {
                                user.setRequestType(RequestType.NULL);
                                output.writeObject(user);
                            }
                            break;
                        case CHAT:
                            new ConnectionHandler();
                            output.writeObject(Server.contacts);
                            break;
                    }
                }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                if (readInput instanceof Message) {
                    Message message = (Message) readInput;
                    String sender = message.getSenderUser().getUsername();
                    String receiver = message.getReceiverUser().getUsername();
                    message.getReceiverUser().setPicture(userMap.get(receiver).getPicture());
                    message.getSenderUser().setPicture(userMap.get(sender).getPicture());
                    message.setReceiverUser(message.getSenderUser());
                    message.setSenderUser(message.getReceiverUser());
                    if (Server.sockets.containsKey(receiver) && Server.sockets.containsKey(sender)) {
                        Server.sockets.get(receiver).output.writeObject(message);
                        Server.sockets.get(sender).output.writeObject(message);
                        Server.sockets.get(receiver).output.reset();
                        Server.sockets.get(sender).output.reset();
                    }
                }
                if (readInput instanceof Group) {
                    group = (Group) readInput;
                    groupMap.put(group.getName(), group);
                    fileHandler.writeObjectToFile(groupMap, groupFileName);
                    String groups = groupMap.get(group.getName()).getName() +
                            "~" + groupMap.get(group.getName()).getImage();
                    Server.contacts.add(groups);
                    output.writeObject(true);
                    new ConnectionHandler();
                }
            }
        } catch (Exception e) {
            System.out.println("Client Closed!");
            e.printStackTrace();
        }
    }

    private boolean foundUser(String username, String password) {
        if (userMap.containsKey(username))
            return userMap.get(username).getPassword().equals(password);
        return false;
    }

    private boolean foundPassword(String username, String name) {
        if (userMap.containsKey(username))
            return userMap.get(username).getName().equals(name);
        return false;
    }

    private synchronized boolean checkDuplicateUsername(String username) {
        return !userMap.containsKey(username);
    }
}
