
package com.codeuptopia.secondhandmarket;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.codeuptopia.secondhandmarket.config.AppConfig;
import com.codeuptopia.secondhandmarket.config.GuiceModule;
import io.javalin.Javalin;

public class App {
    
    public static void main(String[] args) {
        
        // Load configuration
        AppConfig appConfig = new AppConfig();
        
        // Initialize Guice
        Injector injector = Guice.createInjector(new GuiceModule(appConfig));
        
        // Initialize Javalin
        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
            config.registerPlugin(injector.getInstance(SecurityModule.class));
        });
        
        // Register controllers
        app.routes(() -> {
            injector.getInstance(ItemController.class);
            injector.getInstance(UserController.class);
            injector.getInstance(MessageController.class);
            injector.getInstance(PaymentController.class);
            injector.getInstance(SearchController.class);
            injector.getInstance(ReviewController.class);
        });
        
        // Start server
        app.start(appConfig.getPort());
    }
    
}
