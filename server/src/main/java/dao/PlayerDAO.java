package dao;

import dao.impl.IPlayerDAO;
import dao.impl.IUserDAO;
import models.Player;
import models.PlayerFriend;
import models.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO extends DAO implements IPlayerDAO {
    public PlayerDAO(Connection conn){
        this.conn = conn;
        try {
            this.statement = this.conn.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public Player findPlayerByUserId(Long id) {
        String sql = "SELECT id, player_name, status, total_game, total_game_won, total_point, user_id " +
                "FROM player WHERE user_id = ?";
        Player player = new Player();
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setLong(1, id);
            this.rs = this.preStatement.executeQuery();
            if (rs.next()){
                player.setId(rs.getLong("id"));
                player.setPlayerName(rs.getString("player_name"));
                player.setTotalPoint( rs.getDouble("total_point"));
                player.setTotalGame(rs.getLong("total_game"));
                player.setTotalGameWon(rs.getLong("total_game_won"));
                player.setStatus(rs.getString("status"));
            }
            player.setListFriends(getListFriend(player.getId()));
            return player;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<PlayerFriend> getListFriend(Long id ){
        String sql= "Call GET_PLAYER_FRIENDS(?)";
        List<PlayerFriend > friendList = new ArrayList<>();
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setLong(1, id);
            this.rs = this.preStatement.executeQuery();
            if (rs.next()){
                PlayerFriend playerFriend=new PlayerFriend();
                playerFriend.setPlayerName(rs.getString("player_name"));
                playerFriend.setStatus(rs.getString("status"));
                friendList.add(playerFriend);
            }
            return friendList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertPlayer(User user) {
        String sql = "INSERT INTO player (player_name, status, total_game, total_game_won, total_point, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";;
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, user.getUsername());  // set player_name
            this.preStatement.setString(2, "Offline");      // set status
            this.preStatement.setLong(3, 0);     // set total_game
            this.preStatement.setLong(4, 0);  // set total_game_won
            this.preStatement.setDouble(5, 0.0);  // set total_point
            this.preStatement.setLong(6, user.getId());
            this.preStatement.executeUpdate();
            System.out.println("Insert player success");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void setPlayerOnline(User user) {
        String sql = "UPDATE player SET status = ? WHERE user_id = ?";
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, "Online");  // Set status to "Online"
            this.preStatement.setLong(2, user.getId());
            this.preStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public List selectAll() {
        return null;
    }

    @Override
    public Object findById(int id) {
        return null;
    }

    @Override
    public int insert(Object object) {
        return 0;
    }

    @Override
    public int update(Object object) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public void closeConnection() {

    }
}
