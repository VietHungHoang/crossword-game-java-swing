package controller.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;

import models.ObjectWrapper;
import utils.StreamData;

public class SendMessages extends Thread {
    private ObjectOutputStream oos;

    public SendMessages(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public void send(StreamData.Message message, Object object) throws IOException{
        ObjectWrapper objectWrapper = new ObjectWrapper(message.name(), object);
        this.oos.writeObject(objectWrapper);
        oos.flush();
    }

}
