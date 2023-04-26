
package com.codeuptopia.secondhandmarket.service;

import com.google.inject.Inject;
import com.codeuptopia.secondhandmarket.model.Item;
import com.codeuptopia.secondhandmarket.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportService {
    
    private final ItemService itemService;
    private final UserService userService;
    private final AnalyticsService analyticsService;
    
    @Inject
    public ReportService(ItemService itemService, UserService userService, AnalyticsService analyticsService) {
        this.itemService = itemService;
        this.userService = userService;
        this.analyticsService = analyticsService;
    }
    
    public void generateItemReport(File file) throws IOException {
        List<Item> items = itemService.getAllItems();
        FileWriter writer = new FileWriter(file);
        for (Item item : items) {
            writer.write(item.getName() + "," + item.getPrice() + "," + item.getCategory() + "," + item.getCreatedAt() + "\n");
        }
        writer.close();
    }
    
    public void generateUserReport(File file) throws IOException {
        List<User> users = userService.getAllUsers();
        FileWriter writer = new FileWriter(file);
        for (User user : users) {
            writer.write(user.getUsername() + "," + user.getEmail() + "," + user.getCreatedAt() + "\n");
        }
        writer.close();
    }
    
    public void generateAnalyticsReport(File file) throws IOException {
        int itemCount = analyticsService.getItemCount();
        int userCount = analyticsService.getUserCount();
        List<Item> mostViewedItems = analyticsService.getMostViewedItems(10);
        List<User> mostActiveUsers = analyticsService.getMostActiveUsers(10);
        FileWriter writer = new FileWriter(file);
        writer.write("Number of Items: " + itemCount + "\n");
        writer.write("Number of Users: " + userCount + "\n");
        writer.write("Most Viewed Items: \n");
        for (Item item : mostViewedItems) {
            writer.write("- " + item.getName() + ": " + item.getViewCount() + " views\n");
        }
        writer.write("Most Active Users: \n");
        for (User user : mostActiveUsers) {
            writer.write("- " + user.getUsername() + ": " + user.getActivityScore() + " points\n");
        }
        writer.close();
    }
    
}
