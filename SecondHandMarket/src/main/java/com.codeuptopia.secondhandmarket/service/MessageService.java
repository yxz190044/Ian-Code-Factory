
package com.codeuptopia.secondhandmarket.service;

import com.codeuptopia.secondhandmarket.dao.MessageDao;
import com.codeuptopia.secondhandmarket.model.Message;
import com.codeuptopia.secondhandmarket.model.User;

import java.util.List;

public class MessageService {
    private MessageDao messageDao;
    private UserService userService;
    
    public MessageService(MessageDao messageDao, UserService userService) {
        this.messageDao = messageDao;
        this.userService = userService;
    }
    
    public List<Message> getAllMessages() {
        return messageDao.getAllMessages();
    }
    
    public Message getMessageById(int id) {
        return messageDao.getMessageById(id);
    }
    
    public void sendMessage(Message message) {
        messageDao.sendMessage(message);
    }
    
    public void deleteMessage(int id) {
        messageDao.deleteMessage(id);
    }
    
    public List<Message> getMessagesBySender(User sender) {
        // logic for retrieving messages by sender
    }
    
    public List<Message> getMessagesByReceiver(User receiver) {
        // logic for retrieving messages by receiver
    }
    
    public int getUnreadMessageCount(User user) {
        // logic for computing the number of unread messages for a user
    }
    
    // additional methods for computing analytics or performing other business logic
}
