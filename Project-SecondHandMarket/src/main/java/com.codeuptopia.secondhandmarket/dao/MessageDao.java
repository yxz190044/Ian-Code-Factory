package com.codeuptopia.secondhandmarket.dao;

import com.codeuptopia.secondhandmarket.model.Message;

public interface MessageDao {
    Message getMessageById(int id);
    Message createMessage(Message message);
    void updateMessage(Message message);
    void deleteMessage(int id);
}
