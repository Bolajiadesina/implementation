package com.security.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;




@Component
public class DatabaseConnection {
    private final static String url = "jdbc:postgresql://localhost:5432/postgres";
private final static String username = "postgres";
private final static String password = "root";


public  Connection getDBConnection() throws ClassNotFoundException, SQLException {
    Connection conn = null;

    // Load PostgreSQL JDBC driver
    Class.forName("org.postgresql.Driver");

   // System.out.println("Connecting to the database... URL: " + url + ", User: " + username);

    // Establish the connection
    conn = DriverManager.getConnection(url, username, password);

    System.out.println("Connected to the database");
    return conn;
}

}
