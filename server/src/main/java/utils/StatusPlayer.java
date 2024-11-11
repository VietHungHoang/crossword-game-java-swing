package utils;
// su dung cho player
public enum StatusPlayer {
    IN_GAME("In Game","Người chơi đang trong trận"),
    IN_ROOM("In Room","Người chơi đang trong phòng (trong room)"),
    FINDING_GAME("Finding Game","Người chơi đang tìm trận"),
    OFFLINE("Offline", "Người chơi đang ngoại tuyến"),
    ONLINE("Online", "Người chơi đang trực tuyến");
    public final String value;
    public final String description;

    StatusPlayer(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
