
package com.codeuptopia.secondhandmarket.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    
    private Properties properties;
    
    public AppConfig() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public int getPort() {
        return Integer.parseInt(getProperty("server.port"));
    }
    
    public String getDatabaseUrl() {
        return getProperty("database.url");
    }
    
    public String getDatabaseUsername() {
        return getProperty("database.username");
    }
    
    public String getDatabasePassword() {
        return getProperty("database.password");
    }
    
}
