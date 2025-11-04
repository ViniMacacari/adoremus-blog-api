package com.blog.adoremus.api.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Service;

import com.blog.adoremus.api.database.PostgresConnection;

@Service
public class LectioService {

    private final PostgresConnection db;

    public LectioService(PostgresConnection db) {
        this.db = db;
    }

    public void insertLectio(String livro, String passagem, String texto, int biblia, String conteudo) {
        int idGospel = searchOrCreateText(livro, passagem, texto, biblia);

        db.execUpdate(
                "INSERT INTO lectio_divina (id_evangelho, conteudo) VALUES (?, ?)",
                idGospel, conteudo
        );
    }

    private int searchOrCreateText(String livro, String passagem, String texto, int biblia) {
        try (Connection conn = db.connect()) {
            String select = "SELECT id FROM passagens WHERE passagem = ? AND biblia = ?";
            PreparedStatement stmt = conn.prepareStatement(select);
            stmt.setString(1, passagem);
            stmt.setInt(2, biblia);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

            String insert = "INSERT INTO passagens (livro, passagem, texto, biblia) VALUES (?, ?, ?, ?) RETURNING id";
            PreparedStatement psInsert = conn.prepareStatement(insert);
            psInsert.setInt(1, Integer.parseInt(livro));
            psInsert.setString(2, passagem);
            psInsert.setString(3, texto);
            psInsert.setInt(4, biblia);
            ResultSet inserted = psInsert.executeQuery();

            if (inserted.next()) {
                return inserted.getInt("id");
            }

            throw new RuntimeException("Falha ao inserir nova passagem.");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar ou criar passagem", e);
        }
    }
}
