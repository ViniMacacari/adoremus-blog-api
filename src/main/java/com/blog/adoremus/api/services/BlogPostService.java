package com.blog.adoremus.api.services;

import org.springframework.stereotype.Service;

import com.blog.adoremus.api.database.PostgresConnection;

@Service
public class BlogPostService {

    private final PostgresConnection db;

    public BlogPostService(PostgresConnection db) {
        this.db = db;
    }

    public void insertPost(String titulo, String subtitulo, String conteudo, String autor, int categoria) {
        db.execUpdate(
                "INSERT INTO postagens (titulo, subtitulo, conteudo, autor, categoria) VALUES (?, ?, ?, ?, ?)",
                titulo, subtitulo, conteudo, autor, categoria
        );
    }
}
