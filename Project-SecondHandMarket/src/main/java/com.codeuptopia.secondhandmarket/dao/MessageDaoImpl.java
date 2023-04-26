package com.codeuptopia.secondhandmarket.dao;

import com.codeuptopia.secondhandmarket.model.Message;
import com.codeuptopia.secondhandmarket.database.DatabaseConnection;

public class MessageDaoImpl implements MessageDao {
    private DatabaseConnection databaseConnection;
    
    public MessageDaoImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    
    public Message getMessageById(int id) {
        // TODO: Implement getMessageById method
        return null;
    }
    
    public Message createMessage(Message message) {
        // TODO: Implement createMessage method
        return null;
    }
    
    public void updateMessage(Message message) {
        // TODO: Implement updateMessage method
    }
    
    public void deleteMessage(int id) {
        // TODO: Implement deleteMessage method
    }
}
