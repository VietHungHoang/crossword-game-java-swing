package client.models;

import java.io.Serializable;
import java.util.Date;

public class GameRound implements Serializable {
    private Long id;
    private Double point;
    private Date timeRound;
    private Alphabet alphabet;
    private Word word;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public Date getTimeRound() {
        return timeRound;
    }

    public void setTimeRound(Date timeRound) {
        this.timeRound = timeRound;
    }

    public Alphabet getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Alphabet alphabet) {
        this.alphabet = alphabet;
    }


    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public GameRound(Long id, Double point, Date timeRound, Alphabet alphabet, Word word) {
        this.id = id;
        this.point = point;
        this.timeRound = timeRound;
        this.alphabet = alphabet;
        this.word = word;
    }
}
