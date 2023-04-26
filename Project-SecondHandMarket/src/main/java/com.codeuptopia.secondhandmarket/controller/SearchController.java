
package com.codeuptopia.secondhandmarket.controller;

import com.google.inject.Inject;
import com.codeuptopia.secondhandmarket.model.Item;
import com.codeuptopia.secondhandmarket.service.SearchService;
import io.javalin.http.Context;

import java.util.List;

public class SearchController {
    
    private final SearchService searchService;
    
    @Inject
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }
    
    public void searchItems(Context context) {
        String query = context.queryParam("q");
        String category = context.queryParam("category");
        int limit = context.queryParam("limit", Integer.class).orElse(10);
        int offset = context.queryParam("offset", Integer.class).orElse(0);
        List<Item> items = searchService.searchItems(query, category, limit, offset);
        context.json(items);
    }
    
}
