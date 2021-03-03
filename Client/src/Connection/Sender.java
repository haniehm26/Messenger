package Connection;

import Moduls.Message;

import java.io.ObjectOutputStream;

public class Sender extends Thread {
    private Message message;
    private ObjectOutputStream output;

    public Sender(Message message, ObjectOutputStream output) {
        this.output = output;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            if (!this.output.equals(Connection.getInstance().getOutput())) {
                System.out.println("Error!");
                return;
            }
            output.writeObject(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
