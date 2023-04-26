package com.codeuptopia.secondhandmarket.controller;

import com.google.inject.Inject;
import com.codeuptopia.secondhandmarket.model.User;
import com.codeuptopia.secondhandmarket.service.UserService;
import io.javalin.http.Context;

import java.util.List;

public class UserController {
    
    private final UserService userService;
    
    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    public void getUser(Context ctx) {
        String userId = ctx.pathParam("id");
        User user = userService.getUserById(userId);
        if (user != null) {
            ctx.json(user);
        } else {
            ctx.status(404);
        }
    }
    
    public void getAllUsers(Context ctx) {
        List<User> users = userService.getAllUsers();
        ctx.json(users);
    }
    
    public void addUser(Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        userService.addUser(user);
        ctx.status(201);
    }
    
    public void updateUser(Context ctx) {
        String userId = ctx.pathParam("id");
        User user = ctx.bodyAsClass(User.class);
        user.setId(userId);
        userService.updateUser(user);
        ctx.status(204);
    }
    
    public void deleteUser(Context ctx) {
        String userId = ctx.pathParam("id");
        userService.deleteUser(userId);
        ctx.status(204);
    }
    
    // frontend API endpoints
    
    public void getUserByEmail(Context ctx) {
        String email = ctx.pathParam("email");
        User user = userService.getUserByEmail(email);
        if (user != null) {
            ctx.json(user);
        } else {
            ctx.status(404);
        }
    }
    
    public void searchUsers(Context ctx) {
        String query = ctx.queryParam("q");
        List<User> users = userService.searchUsers(query);
        ctx.json(users);
    }
    
}
