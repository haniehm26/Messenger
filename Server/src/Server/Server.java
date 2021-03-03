package Server;

import Handlers.ConnectionHandler;
import Moduls.Message;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class Server {
    public static ConcurrentSkipListSet<String> contacts = new ConcurrentSkipListSet<>();
    public static ConcurrentHashMap<String, ConnectionHandler> sockets = new ConcurrentHashMap<>();
    public static CopyOnWriteArrayList<ConnectionHandler> connectionHandlers = new CopyOnWriteArrayList<>();
    public static LinkedBlockingDeque<Message> messages = new LinkedBlockingDeque<>();

    public static void main(String[] args) {
        System.out.println("Waiting for Client!");
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ConnectionHandler connectionHandler = new ConnectionHandler(socket);
                new Thread(connectionHandler).start();
                connectionHandlers.add(connectionHandler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}