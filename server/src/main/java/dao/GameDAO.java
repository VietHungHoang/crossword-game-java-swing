package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import models.Game;
import models.User;

public class GameDAO extends DAO {
    public GameDAO(Connection conn){
        this.conn = conn;
        try {
            this.statement = this.conn.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int insert(Game game) {
        String sql = "INSERT INTO game (end_date, start_date, status, type, winner) " +
                "VALUES (?, ?, ?, ?, ?)";;
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setTimestamp(1,new java.sql.Timestamp(System.currentTimeMillis()));  // set player_name// set status
            this.preStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));     // set total_game
            this.preStatement.setString(3, game.getStatus());  // set total_game_won
            this.preStatement.setString(4, game.getType());  // set total_point
            this.preStatement.setLong(5, game.getWinner());
            this.preStatement.executeUpdate();
            System.out.println("Insert game success");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return 0;
    }

    @Override
    public User findById(int id){
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void closeConnection() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int delete(int id) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int insert(Object object) {
        return 0;
    }

    @Override
    public List selectAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int update(Object object) {
        // TODO Auto-generated method stub
        return 0;
    };
}
