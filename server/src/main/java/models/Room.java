package models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room implements Serializable {

  private String id;
  private Date createAt;
  private Player createBy;
  private List<Player> players;
  private String status;
  private Map<Player, Boolean> playerReadyStatus = new HashMap<>();
  private boolean isRanking;
  private static final long serialVersionUID = 1L;
    public Room(String id, Date createAt, Player createBy, List<Player> players, String status, boolean isRanking) {
        this.id = id;
        this.createAt = createAt;
        this.createBy = createBy;
        this.players = players;
        this.status = status;
        this.isRanking = isRanking;
        // Khởi tạo trạng thái ready của tất cả người chơi là false
        for (Player player : players) {
            playerReadyStatus.put(player, false);
        }
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        playerReadyStatus.put(player, false); // Mặc định chưa sẵn sàng khi mới vào phòng
    }

    public void setPlayerReady(Player player) {
        playerReadyStatus.put(player, true);
    }

    public boolean areBothPlayersReady() {
        return playerReadyStatus.values().stream().filter(ready -> ready).count() == 2;
    }
  public Room() {
  }

  public String getId() {
      return id;
  }

  public void setId(String id) {
      this.id = id;
  }

  public Date getCreateAt() {
      return createAt;
  }

  public void setCreateAt(Date createAt) {
      this.createAt = createAt;
  }

  public Player getCreateBy() {
      return createBy;
  }

  public void setCreateBy(Player createBy) {
      this.createBy = createBy;
  }

  public List<Player> getPlayers() {
      return players;
  }

  public void setPlayers(List<Player> players) {
      this.players = players;
  }

  public String getStatus() {
      return status;
  }

  public void setStatus(String status) {
      this.status = status;
  }

  public Object[] toObject() {
      return new Object[]{id, createBy.getPlayerName(), status};
  }
  public void removePlayer(Player player) {
      this.players.remove(player);
  }
  public void setRanking(boolean isRanking) {
      this.isRanking = isRanking;
  }
  public boolean isRanking() {
      return isRanking;
  }
  @Override
  //Phải in ra đủ các Player trong room
  public String toString() {
      return "Room{" +
              "id='" + id + '\'' +
              ", createAt=" + createAt +
              ", createBy=" + createBy +
              ", players=" + players +
              ", status='" + status + '\'' +
              ", isRanking=" + isRanking +
              ", playerReadyStatus=" + playerReadyStatus +
              '}';
  }
}
