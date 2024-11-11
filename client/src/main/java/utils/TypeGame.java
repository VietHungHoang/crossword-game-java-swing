package utils;


// sử dụng cho model game va room
public enum TypeGame {
    RANKED_MATCH("Ranked Match","Đánh rank"),
    FRIEND_MATCH("Friend Match","Đánh với bạn bè");
    public final String value;
    public final String description;

    TypeGame(String value, String description) {
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
