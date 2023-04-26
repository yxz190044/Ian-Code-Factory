package com.codeuptopia.secondhandmarket.database;

import com.google.inject.Singleton;

@Singleton
public class DatabaseProperties {
    
    private final String url;
    private final String username;
    private final String password;
    
    public DatabaseProperties() {
        // TODO: replace these values with the actual credentials for your Google Cloud MySQL database
        this.url = "jdbc:mysql://<database-ip-address>:<database-port>/<database-name>?useSSL=false";
        this.username = "<database-username>";
        this.password = "<database-password>";
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
}
