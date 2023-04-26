
package com.codeuptopia.secondhandmarket.service;

import com.codeuptopia.secondhandmarket.dao.UserDao;
import com.codeuptopia.secondhandmarket.model.User;

public class UserService {
    private UserDao userDao;
    
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }
    
    public User createUser(User user) {
        return userDao.createUser(user);
    }
    
    public void updateUser(User user) {
        userDao.updateUser(user);
    }
    
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }
}
