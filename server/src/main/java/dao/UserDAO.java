package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import dao.impl.IUserDAO;
import models.Player;
import models.User;

public class UserDAO extends DAO implements IUserDAO{

    public UserDAO(Connection conn){
        this.conn = conn;
        try {
            this.statement = this.conn.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM user WHERE user_name = ?";
        try{
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, username);
            this.rs = this.preStatement.executeQuery();
            if (rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(User user) {
        String sql = "INSERT INTO user (user_name, password) VALUES (?,?)";
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setString(1, user.getUsername());
            this.preStatement.setString(2, user.getPassword());
            this.preStatement.executeUpdate();
            System.out.println("Insert user success");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
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
        // TODO Auto-generated method stub
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
