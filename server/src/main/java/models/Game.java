package models;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import utils.RandomString;

public class Game implements Serializable{
    private String id; // ID của game
    private Date endDate; // Thời gian kết thúc game
    private Date startDate; // Thời gian bắt đầu game
    private String status; // Trạng thái game (Pending, Completed, ...)
    private String type; // Loại game (BO3)
    private Long winner; // ID của người thắng
    private List<WordInGame> wordInGameList; // Danh sách từ trong game
    private List<GameRound> gameRoundList; // Danh sách các round
    private Room room; // Phòng chơi game
    public Player player1; // Người chơi 1
    public Player player2; // Người chơi 2
    private int player1Score = 0; // Điểm của người chơi 1
    private int player2Score = 0; // Điểm của người chơi 2
    private String roomId; // ID của phòng
    private static final long serialVersionUID = 1L;

    public Game(Room room) {
        RandomString randomString = new RandomString(9, new SecureRandom(), RandomString.DIGITS);
        this.id = randomString.nextString(); // Tạo ID game ngẫu nhiên
        this.room = room; // Lưu thông tin phòng
        this.startDate = new Date(); // Ghi nhận thời gian bắt đầu
        this.status = "Pending"; // Trạng thái ban đầu là Pending
        this.type = "BO3"; // Loại game BO3
        this.roomId = room.getId();

        // Lấy hai người chơi từ Room
        this.player1 = room.getPlayers().get(0);
        this.player2 = room.getPlayers().get(1);
        this.winner = null;
        
        // Khởi tạo danh sách từ ngẫu nhiên cho WordInGame và danh sách GameRound rỗng
        this.wordInGameList = WordInGame.generateSampleData();
        this.gameRoundList = new ArrayList<>();

        // Thêm GameRound đầu tiên
        addNewRound();
    }

    // Kiểm tra xem có người chơi nào thắng chưa
    public boolean checkWinner() {
        return player1Score == 2 || player2Score == 2;
    }

    // Thêm một GameRound mới
    public void addNewRound() {
        if (checkWinner()) {
            return; // Nếu đã có người thắng thì không thêm round mới
        }

        WordInGame newWord = getUniqueKeyword(); // Lấy từ khóa ngẫu nhiên chưa được sử dụng
        if (newWord != null) {
            // Tạo GameRound mới với gameId và WordInGame
            GameRound newRound = new GameRound(this.id, newWord);
            gameRoundList.add(newRound); // Thêm round mới vào danh sách
        }
    }

    // Lấy từ khóa ngẫu nhiên chưa tồn tại trong game
    private WordInGame getUniqueKeyword() {
        List<WordInGame> shuffledWords = new ArrayList<>(wordInGameList);
        Collections.shuffle(shuffledWords); // Xáo trộn danh sách từ

        for (WordInGame word : shuffledWords) {
            if (isUniqueKeyword(word)) {
                return word; // Trả về từ khóa duy nhất
            }
        }
        return null; // Không tìm thấy từ khóa duy nhất
    }

    // Kiểm tra xem từ khóa có duy nhất không
    private boolean isUniqueKeyword(WordInGame newWord) {
        for (GameRound existingRound : gameRoundList) {
            if (existingRound.getWordInGame().getKeyword().equalsIgnoreCase(newWord.getKeyword())) {
                return false; // Từ khóa đã tồn tại trong game
            }
        }
        return true; // Từ khóa là duy nhất
    }

    // Cộng điểm cho người chơi dựa trên ID
    public void addPoint(String playerId) {
        if (player1.getId().toString().equals(playerId)) {
            player1Score++; // Cộng điểm cho người chơi 1
        } else if (player2.getId().toString().equals(playerId)) {
            player2Score++; // Cộng điểm cho người chơi 2
        }
        
        // Kiểm tra xem có ai thắng không sau khi cộng điểm
        if (checkWinner()) {
            endGame(player1Score == 2 ? player1 : player2); // Kết thúc game nếu có người thắng
        }
    }

    // Kết thúc round và cập nhật điểm cho người thắng
    public void endRound(GameRound round, Player winner) {
        addPoint(winner.getId().toString()); // Cộng điểm cho người thắng
        addNewRound(); // Thêm round mới nếu chưa có người thắng
    }

    // Kết thúc game và cập nhật thông tin người thắng
    private void endGame(Player winner) {
        this.endDate = new Date(); // Ghi nhận thời gian kết thúc
        this.status = "Completed"; // Đánh dấu trạng thái là Completed
        this.winner = winner.getId(); // Lưu ID của người thắng
        winner.setTotalGameWon(winner.getTotalGameWon() + 1); // Cập nhật số game thắng cho người chơi
    }

    // Getter cho danh sách các round
    public List<GameRound> getGameRoundList() {
        return gameRoundList;
    }
    public String getRoomId() {
        return roomId;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
