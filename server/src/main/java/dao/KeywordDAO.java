package dao;

import dao.impl.IKeywordDAO;
import models.Keyword;
import models.Player;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KeywordDAO extends DAO implements IKeywordDAO {
    public KeywordDAO(Connection conn){
        this.conn = conn;
        try {
            this.statement = this.conn.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @Override
    public Keyword findById(Long id){
        String sql = "SELECT * " +
                "FROM keywords WHERE id = ?";
        Keyword keyword = new Keyword();
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.preStatement.setLong(1, id);
            this.rs = this.preStatement.executeQuery();
            if (rs.next()){
                keyword.setId(rs.getLong("id"));
                keyword.setValue(rs.getString("value"));
            }
            return keyword;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) AS total FROM keywords";
        int totalCount = 0;
        try {
            this.preStatement = this.conn.prepareStatement(sql);
            this.rs = this.preStatement.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt("total");
            }
            return totalCount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Đảm bảo đóng các tài nguyên
            try {
                if (rs != null) rs.close();
                if (preStatement != null) preStatement.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
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
