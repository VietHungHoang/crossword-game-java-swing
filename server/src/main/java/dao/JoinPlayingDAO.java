package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import models.JoinPlaying;
import models.User;

public class JoinPlayingDAO extends DAO {

    public JoinPlayingDAO(Connection conn){
        this.conn = conn;
        try {
            this.statement = this.conn.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insert(Long gameId, Long playerId) {
        String sql = "INSERT INTO join_playing (game_id, player_id) VALUES (?,?)";
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setLong(1, gameId);
            this.preStatement.setLong(2, playerId);
            this.preStatement.executeUpdate();
            System.out.println("Insert user success");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List selectAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
    }

    @Override
    public Object findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public int insert(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public int update(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void closeConnection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'closeConnection'");
    }
    
}
