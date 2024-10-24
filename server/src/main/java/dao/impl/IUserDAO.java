package dao.impl;

import models.User;

public interface IUserDAO {
    User findByUsername(String usename);
    void insert(User user);
}
