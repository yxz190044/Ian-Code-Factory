
package com.codeuptopia.secondhandmarket.dao;

import com.codeuptopia.secondhandmarket.model.User;

public interface UserDao {
    
    User getUserById(int id);
    
    User getUserByEmail(String email);
    
    User createUser(User user);
    
    User updateUser(User user);
    
    void deleteUser(User user);
    
}
