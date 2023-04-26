package com.codeuptopia.secondhandmarket.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class DatabaseConnection {
    
    private final DatabaseProperties databaseProperties;
    
    @Inject
    public DatabaseConnection(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                                           databaseProperties.getUrl(),
                                           databaseProperties.getUsername(),
                                           databaseProperties.getPassword());
    }
}

