package com.codeuptopia.secondhandmarket.controller;

import com.google.inject.Inject;
import com.codeuptopia.secondhandmarket.model.Message;
import com.codeuptopia.secondhandmarket.service.MessageService;
import io.javalin.http.Context;

import java.util.List;

public class MessageController {
    
    private final MessageService messageService;
    
    @Inject
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
    
    public void getMessage(Context ctx) {
        String messageId = ctx.pathParam("id");
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(404);
        }
    }
    
    public void getAllMessages(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }
    
    public void addMessage(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
        messageService.addMessage(message);
        ctx.status(201);
    }
    
    public void deleteMessage(Context ctx) {
        String messageId = ctx.pathParam("id");
        messageService.deleteMessage(messageId);
        ctx.status(204);
    }
    
}
