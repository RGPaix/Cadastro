package com.cadastro.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private ConnectionFactory (){};
    public static Connection getConnection() {
        try {
            System.out.println("Conectando ao banco de dados...");
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Mensageria", "postgres", "123456");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

