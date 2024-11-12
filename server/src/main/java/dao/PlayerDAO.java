package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.impl.IPlayerDAO;
import models.*;

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
    public boolean updatePlayer(Player player){
        System.out.println("Update player có id: " + player.getId());
        System.out.println("Update player total game: " + player.getTotalGame());
        System.out.println("Update player total game won: " + player.getTotalGameWon());
        System.out.println("Update player total point: " + player.getTotalPoint());
            String sql = "UPDATE player SET total_game = ?, total_game_won = ?,total_point=? WHERE id = ?";
            try {
                this.preStatement = this.conn.prepareStatement(sql);
                this.preStatement.setLong(1, player.getTotalGame());
                this.preStatement.setLong(2, player.getTotalGameWon());
                this.preStatement.setLong(3, player.getTotalPoint());
                this.preStatement.setLong(4, player.getId());
    
                int rowsUpdated = this.preStatement.executeUpdate();
                System.out.println(rowsUpdated);
                return rowsUpdated > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }

    @Override
    public List<MatchHistory> getMatchHistory(Long id) {
        String sql = "SELECT  " +
                "    p_opponent.player_name AS opponent, " +
                "    g.start_date AS start_date, " +
                "    g.end_date AS end_date, " +
                "    g.type AS type, " +
                "    IFNULL(p_winner.player_name, '0') AS winner   " +
                "FROM  " +
                "    join_playing jp " +
                "JOIN  " +
                "    join_playing jp_opponent ON jp.game_id = jp_opponent.game_id " +
                "JOIN  " +
                "    player p_opponent ON jp_opponent.player_id = p_opponent.id " +
                "JOIN  " +
                "    game g ON jp.game_id = g.id " +
                "LEFT JOIN  " +
                "    player p_winner ON g.winner = p_winner.id " +
                "WHERE  " +
                "    jp.player_id = ? AND jp_opponent.player_id != ?;  ";
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setLong(1, id);
            this.preStatement.setLong(2, id);
            this.rs = this.preStatement.executeQuery();
            List<MatchHistory> matchHistories = new ArrayList<>();
            while (rs.next()) {  // change from if to while to handle multiple rows
                MatchHistory matchHistory = new MatchHistory();
                matchHistory.setOpponent(rs.getString("opponent"));
                matchHistory.setStartDate(rs.getTimestamp("start_date"));
                matchHistory.setEndDate(rs.getTimestamp("end_date"));
                matchHistory.setType(rs.getString("type"));
                matchHistory.setStatusS(rs.getString("winner"));
                matchHistories.add(matchHistory);
            }
            return matchHistories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                player.setTotalPoint( rs.getInt("total_point"));
                player.setTotalGame(rs.getLong("total_game"));
                player.setTotalGameWon(rs.getLong("total_game_won"));
                player.setStatus(rs.getString("status"));
            }
            return player;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<PlayerFriend> getListFriend(Long id ){
        String sql= "Call GET_PLAYER_FRIENDS(?)";
        List<PlayerFriend > friendList = new ArrayList<>();
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setLong(1, id);
            this.rs = this.preStatement.executeQuery();
            while (rs.next()){
                PlayerFriend playerFriend=new PlayerFriend();
                playerFriend.setId(rs.getLong("id"));
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
        String sql = "INSERT INTO player (player_name, total_game, total_game_won, total_point, user_id) " +
                "VALUES (?, ?, ?, ?, ?)";;
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, user.getUsername());  // set player_name// set status
            this.preStatement.setLong(2, 0);     // set total_game
            this.preStatement.setLong(3, 0);  // set total_game_won
            this.preStatement.setDouble(4, 0.0);  // set total_point
            this.preStatement.setLong(5, user.getId());
            this.preStatement.executeUpdate();
            System.out.println("Insert player success");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<PlayerRanking> getRanking() {
        String sql = "SELECT player_name, (total_game_won * 100 / total_game) AS percent_win, " +
                "total_game_won, total_game, total_point FROM player " +
                "WHERE total_game != 0 " +
                "ORDER BY total_point DESC, total_game DESC, total_game_won DESC";
        List<PlayerRanking> playerRankingList = new ArrayList<>();

        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.rs = this.preStatement.executeQuery();

            while (rs.next()) {
                PlayerRanking playerRanking = new PlayerRanking();
                playerRanking.setPlayerName(rs.getString("player_name"));
                playerRanking.setPercentWin(rs.getDouble("percent_win"));
                playerRanking.setTotalWin(rs.getLong("total_game_won"));
                playerRanking.setTotalGame(rs.getLong("total_game"));
                playerRanking.setTotalPoint(rs.getLong("total_point"));
                playerRankingList.add(playerRanking);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching player rankings", e);
        } finally {
            // Clean up resources, if necessary
            if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
            if (preStatement != null) try { preStatement.close(); } catch (SQLException ignored) {}
        }

        return playerRankingList;
    }

    @Override
    public void makeFriend(Long id, Long friendId) {
        String sql = "Call ADD_FRIEND(?,?)";
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setLong(1, id);
            this.preStatement.setLong(2, friendId);
            this.preStatement.executeUpdate();
            System.out.println("Add friend success");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }



    @Override
    public boolean isFriend(Long id, Long friendId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM list_friends WHERE player_id = ? AND friend_id = ?) AS is_friend";
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setLong(1, id);
            this.preStatement.setLong(2, friendId);
            this.rs = this.preStatement.executeQuery();
                if (rs.next()) {
                    return rs.getBoolean("is_friend");
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if an exception occurs or no result found
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
