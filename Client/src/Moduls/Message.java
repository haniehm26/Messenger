package Moduls;

import java.io.Serializable;
import java.util.Date;

public class Message extends Request implements Serializable {
    private static long serialVersionUID = 1L;
    private User senderUser;
    private User receiverUser;
    private Object data;
    private Date date;
    private MessageType messageType;

    public User getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(User senderUser) {
        this.senderUser = senderUser;
    }

    public User getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(User receiverUser) {
        this.receiverUser = receiverUser;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return getData() + " , " + getDate();
    }
}
