package Connection;

import Moduls.Message;

import java.io.ObjectInputStream;

public class Receiver extends Thread {

    private ObjectInputStream in;
    private Message message;


    public Receiver(ObjectInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            Object read = in.readObject();
            message = (Message) read;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Message message() {
        return message;
    }
}


