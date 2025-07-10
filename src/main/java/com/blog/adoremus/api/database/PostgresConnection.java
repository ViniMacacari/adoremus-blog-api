package com.blog.adoremus.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PostgresConnection {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public Connection connect() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public void execUpdate(String sql, Object... params) {
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
            System.out.println("Execução de update/insert concluída com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao executar update: " + e.getMessage());
            throw new RuntimeException("Erro ao executar update no banco de dados", e);
        }
    }

    public void query(String sql, Object... params) {
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int column = meta.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= column; i++) {
                    System.out.print(meta.getColumnLabel(i) + ": " + rs.getString(i) + "  ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar SELECT: " + e.getMessage());
            throw new RuntimeException("Erro ao executar consulta no banco de dados", e);
        }
    }

    public ResultSet execQuery(String sql, Object... params) {
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao executar SELECT com parâmetros", e);
        }
    }
}
