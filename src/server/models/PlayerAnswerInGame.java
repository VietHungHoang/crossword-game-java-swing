package server.models;

import java.io.Serializable;
public class PlayerAnswerInGame implements  Serializable{
    private Long id;
    private String isSolve;
    private String timeAnswer;
    private String wordAnswer;
    private GameRound gameRound;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsSolve() {
        return isSolve;
    }

    public void setIsSolve(String isSolve) {
        this.isSolve = isSolve;
    }

    public String getTimeAnswer() {
        return timeAnswer;
    }

    public void setTimeAnswer(String timeAnswer) {
        this.timeAnswer = timeAnswer;
    }

    public String getWordAnswer() {
        return wordAnswer;
    }

    public void setWordAnswer(String wordAnswer) {
        this.wordAnswer = wordAnswer;
    }

    public GameRound getGameRound() {
        return gameRound;
    }

    public void setGameRound(GameRound gameRound) {
        this.gameRound = gameRound;
    }

    public PlayerAnswerInGame(Long id, String isSolve, String timeAnswer, String wordAnswer, GameRound gameRound) {
        this.id = id;
        this.isSolve = isSolve;
        this.timeAnswer = timeAnswer;
        this.wordAnswer = wordAnswer;
        this.gameRound = gameRound;
    }
}
