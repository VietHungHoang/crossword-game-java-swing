package controller;

import java.security.SecureRandom;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.ObjectWrapper;
import models.Player;
import models.Room;
import utils.RandomString;
import utils.StreamData;
import views.ServerView;

public class RoomController {
    private ServerView view;
    private Connection conn;
    private SocketHandlers socketHandlers;
    

    public RoomController(ServerView view, Connection conn, SocketHandlers socketHandlers) {
        this.view = view;
        this.conn = conn;
        this.socketHandlers = socketHandlers;
    }
    
    public void createRoom() {
        RandomString randomString = new RandomString(9, new SecureRandom(), RandomString.DIGITS);
        String randomId = randomString.nextString();
        List<Player> playersInRoom = new ArrayList<>();
        playersInRoom.add(this.socketHandlers.getLoginController().getPlayerLogin());
        Room room = new Room(randomId, new Date(), this.socketHandlers.getLoginController().getPlayerLogin(), playersInRoom, "1/2");
        this.socketHandlers.getLoginController().getPlayerLogin().setStatus("Trong phòng");
        ServerController.rooms.add(room);
        // this.idRoom = randomId;
        ObjectWrapper objectWrapper = new ObjectWrapper(StreamData.Message.CREATE_ROOM.name(), room);
        socketHandlers.send(objectWrapper);
        System.out.println("Sent create room " + room);
    }
    


}
