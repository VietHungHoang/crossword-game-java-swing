package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Game {
    private Long id;
    private Date endDate;
    private Date startDate;
    private String status;
    private String type;
    private Long winner;
    private WordInGame[] wordInGame;
    private List<GameRound> gameRoundList;
    private Room room;
    public Game(Long id, Date endDate, Date startDate, String status, String type, Long winner) {
        this.id = id;
        this.endDate = endDate;
        this.startDate = startDate;
        this.status = status;
        this.type = type;
        this.winner = winner;

        // Tự động khởi tạo 3 đối tượng WordInGame và GameRound
        this.wordInGame = createRandomWordInGameArray();
        this.gameRoundList = createGameRoundList(this.wordInGame);

    }

    private WordInGame[] createRandomWordInGameArray() {
        // Giả lập danh sách WordInGame với dữ liệu cứng
        List<WordInGame> wordList = WordInGame.generateSampleData();

        // Xáo trộn danh sách để lấy ngẫu nhiên
        Collections.shuffle(wordList);

        // Lấy 3 đối tượng ngẫu nhiên từ danh sách đã xáo trộn
        return new WordInGame[] { wordList.get(0), wordList.get(1), wordList.get(2) };
    }

    private List<GameRound> createGameRoundList(WordInGame[] wordInGameArray) {
        List<GameRound> rounds = new ArrayList<>();

        // Tạo 3 đối tượng GameRound và mỗi đối tượng lấy một WordInGame tương ứng
        for (WordInGame word : wordInGameArray) {
        }
        return rounds;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getWinner() {
        return winner;
    }

    public void setWinner(Long winner) {
        this.winner = winner;
    }

    public WordInGame[] getWordInGame() {
        return wordInGame;
    }

    public List<GameRound> getGameRoundList() {
        return gameRoundList;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", endDate=" + endDate +
                ", startDate=" + startDate +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", winner=" + winner +
                ", wordInGame=" + Arrays.toString(wordInGame) +
                ", gameRoundList=" + gameRoundList +
                '}';
    }
}
