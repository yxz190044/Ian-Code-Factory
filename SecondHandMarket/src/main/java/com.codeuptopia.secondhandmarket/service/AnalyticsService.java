
package com.codeuptopia.secondhandmarket.service;

import com.google.inject.Inject;
import com.codeuptopia.secondhandmarket.model.Item;
import com.codeuptopia.secondhandmarket.model.User;
import java.util.List;

public class AnalyticsService {
    
    private final ItemService itemService;
    private final UserService userService;
    
    @Inject
    public AnalyticsService(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }
    
    public int getItemCount() {
        return itemService.getAllItems().size();
    }
    
    public int getUserCount() {
        return userService.getAllUsers().size();
    }
    
    public List<Item> getMostViewedItems(int limit) {
        return itemService.getMostViewedItems(limit);
    }
    
    public List<User> getMostActiveUsers(int limit) {
        return userService.getMostActiveUsers(limit);
    }
    
}
