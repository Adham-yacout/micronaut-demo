package com.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5433/micronaut_db",
                "micronaut_user",
                "micronaut_pass");

        System.out.println("CONNECTED");
        conn.close();
    }
}