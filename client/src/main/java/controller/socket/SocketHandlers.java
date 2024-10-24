package controller.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketHandlers {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private SendMessages sendMessages;
    private ReceiveMessages receiveMessages;
    public boolean connect(String address, int port){
        try {
            this.socket = new Socket(address, port);
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            
            this.sendMessages = new SendMessages(oos);
            this.sendMessages.start();

            this.receiveMessages = new ReceiveMessages(ois);
            this.receiveMessages.start();
            return true; 
        }
        catch (IOException e){
            System.out.println(e);
           return false; 
        }
    }
    public ObjectInputStream getOis() {
        return ois;
    }
    public ObjectOutputStream getOos() {
        return oos;
    }
    public SendMessages getSendMessages() {
        return sendMessages;
    }

    public ReceiveMessages getReceiveMessages() {
        return receiveMessages;
    }
}