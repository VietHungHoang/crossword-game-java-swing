package models;

import java.io.Serializable;

public class PlayerStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private boolean isFriend;
    private String status;
// nguoi choi
    public PlayerStatus(String name, boolean isFriend, String status) {
        this.name = name;
        this.isFriend = isFriend;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public String getStatus() {
        return status;
    }
}
