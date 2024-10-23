package models;

import java.io.Serializable;

public class JoinPlaying  implements Serializable {
    private Long id;
    private Game game;
    private Player player;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public JoinPlaying(Long id, Game game, Player player) {
        this.id = id;
        this.game = game;
        this.player = player;
    }
}


