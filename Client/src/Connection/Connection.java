package Connection;

import Moduls.Message;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

public class Connection {
    private static Connection ourInstance = new Connection();
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean connecting = true;
    private Sender sender;
    private Receiver receiver;

    private Connection() {
    }

    public static Connection getInstance() {
        return ourInstance;
    }

    public void connectToServer(String IP, int Port) {
        Connection connection = Connection.getInstance();
        while (connecting) {
            try {
                connection.socket = new Socket(IP, Port);
                connection.output = new ObjectOutputStream(connection.socket.getOutputStream());
                connection.input = new ObjectInputStream(connection.socket.getInputStream());
                if (connection.socket.isConnected()) {
                    connecting = false;
                    break;
                }
            } catch (Exception e) {
                retryConnecting();
            }
        }
    }

    public void sendToServer(Object data) {
        try {
            output.writeObject(data);
            output.flush();
        } catch (IOException e) {
            connecting = true;
        }
    }

    public Object receiveFromServer() throws IOException, ClassNotFoundException {
        return input.readObject();
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void startSender(Message message) {
        sender = new Sender(message, output);
        sender.start();
    }

    public void startReceiver() {
        receiver = new Receiver(input);
        receiver.start();
        try {
            receiver.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Message message() {
        return receiver.message();
    }

    public void retryConnecting() {
        ButtonType Retry = new ButtonType("Retry", ButtonBar.ButtonData.OK_DONE);
        ButtonType Close = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "Server is not connected, Retry?", Retry, Close);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == Retry) {
            System.out.println("Retry!");
        } else if (result.get() == Close) {
            System.out.println("Closed!");
            System.exit(0);
        }
    }
}
