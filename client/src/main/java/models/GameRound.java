package models;

import java.io.Serializable;

public class GameRound implements Serializable {
    private String gameId; // ID của game
    private WordInGame wordInGame; // Đối tượng WordInGame tương ứng
    private static final long serialVersionUID = 1L;
    // Constructor
    public GameRound(String gameId, WordInGame wordInGame) {
        this.gameId = gameId;
        this.wordInGame = wordInGame;
    }

    // Getter cho gameId
    public String getGameId() {
        return gameId;
    }

    // Getter cho wordInGame
    public WordInGame getWordInGame() {
        return wordInGame;
    }

    @Override
    public String toString() {
        return "GameRound{" +
                "gameId='" + gameId + '\'' +
                ", wordInGame=" + wordInGame +
                '}';
    }
}
