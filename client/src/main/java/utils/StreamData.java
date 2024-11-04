package utils;
public class StreamData {

    public enum Message {
     
        CONNECT_SERVER, 
        LOGIN, 
        SIGNUP, 
        LOGOUT, 
        LIST_ROOM, 
        LIST_ONLINE, 
        CREATE_ROOM, 
        JOIN_ROOM, 
        CHECK_ROOM,
        CHAT_ROOM, 
        LEAVE_ROOM, 
        START,
        MOVE,
        PLAYER_STAT,
        UPDATE_PROFILE,
        SEND_INVITE_FRIEND,
        ACCEPT_INVITE_FRIEND,
        LIST_FRIEND,
        CHECK_FRIEND,
        SEND_INVITE_ROOM,
        FIND_MATCH,
        ACCEPT_FIND_MATCH,
        TIME_FIND_MATCH,
        UNKNOW_MESSAGE,
        HOME, 
        WAITING_FOR_GAME,
        GAME,
        FIND_GAME_SUCCESS,
        FIND_GAME_FAIL,
        PLAYER_READY,
        CANCEL_WAITING,
        OPPONENT_READY,
        START_GAME,
        RANKING,
        LIST_PLAYER,
        UPDATE_LIST_PLAYER,
        INVITE_ROOM,
        INVITE_FRIEND_TO_ROOM,
        GET_LIST_FRIEND,
        RECEIVE_INVITE_ROOM,
        ACCEPT_INVITE_ROOM,
        UPDATE_INVITE_ROOM
    }

    public static Message getStreamMessage(String message) {
        Message result = Message.UNKNOW_MESSAGE;
        try {
            result = Enum.valueOf(StreamData.Message.class, message);
        } catch (Exception e) {
            System.err.println("Unknow message: " + e.getMessage());
        }

        return result;
    }

    public static Message getMessageFromData(String data) {
        String typeStr = data.split(";")[0];
        return getStreamMessage(typeStr);
    }
}
