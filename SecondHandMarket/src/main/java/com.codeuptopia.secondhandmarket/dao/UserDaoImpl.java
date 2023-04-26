package com.codeuptopia.secondhandmarket.dao;

import com.codeuptopia.secondhandmarket.model.User;
import com.codeuptopia.secondhandmarket.database.DatabaseConnection;

public class UserDaoImpl implements UserDao {
    private DatabaseConnection databaseConnection;
    
    public UserDaoImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    
    public User getUserById(int id) {
        // TODO: Implement getUserById method
        return null;
    }
    
    public User createUser(User user) {
        // TODO: Implement createUser method
        return null;
    }
    
    public void updateUser(User user) {
        // TODO: Implement updateUser method
    }
    
    public void deleteUser(int id) {
        // TODO: Implement deleteUser method
    }
}
