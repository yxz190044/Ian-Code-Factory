package com.codeuptopia.secondhandmarket.dao;

import com.codeuptopia.secondhandmarket.model.User;

public interface UserDao {
    User getUserById(int id);
    User createUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
}
