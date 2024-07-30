package com.security.implementation.security.basicAuth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final static String url = "jdbc:postgresql://localhost:5432/postgres";
    private final static String usernames = "postgres";
    private final static String password = "root";
    private final static String host = "localhost";
    private final static String Instance = "postgresql";
    


    public static Connection getDBConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, Exception {
        Connection conn = null;
       
        

        //ubslive
        Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        System.out.println("Connecting to the database..." + host + " : ---- : " + usernames);
        //conn = DriverManager.getConnection(host, usernames, (passwords));
        conn = DriverManager.getConnection(
                "jdbc:postgresql:@" + host + ":5432:" + Instance + "", usernames, password);
        System.out.println("Connected to the database");
        return conn;
    }
}
