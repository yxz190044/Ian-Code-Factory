
package com.codeuptopia.secondhandmarket.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.codeuptopia.secondhandmarket.controller.ItemController;
import com.codeuptopia.secondhandmarket.dao.ItemDao;
import com.codeuptopia.secondhandmarket.model.Item;
import com.codeuptopia.secondhandmarket.service.ItemService;


public class ItemModule extends AbstractModule {
    
    @Override
    protected void configure() {
        bind(ItemService.class).to(ItemServiceImpl.class).in(Singleton.class);
        bind(ItemController.class);
    }
    
    @Provides
    @Singleton
    public Router provideRouter(ItemController controller) {
        Router router = Router.router(vertx);
        router.get("/api/items/:id").handler(controller::getItem);
        // defining API endpoints, router.get("/api/items/:id") is a method call that sets up a GET endpoint for the "/api/items/:id" URL path.
        // For example, a GET request to "/api/items/123" would match this endpoint and pass "123" as the value of the "id" parameter.
        // When a GET request is made to this endpoint, the router will call the appropriate handler method in the associated controller, passing in the HTTP context and any parameters that were specified in the URL path or query string.
        // Overall, this code sets up a RESTful API endpoint for retrieving a specific item by its ID, using the GET HTTP method and the "/api/items/:id" URL path.
        router.get("/api/items").handler(controller::getAllItems);
        router.post("/api/items").handler(controller::addItem);
        router.put("/api/items/:id").handler(controller::updateItem);
        router.delete("/api/items/:id").handler(controller::deleteItem);
        router.get("/api/items/id/:id").handler(controller::getItemById);
        router.get("/api/items/category/:category").handler(controller::getAllItemsByCategory);
        router.get("/api/items/search").handler(controller::searchItems);
        return router;
    }
}
