package com.blog.adoremus.api.services;

import org.springframework.stereotype.Service;

import com.blog.adoremus.api.database.PostgresConnection;

@Service
public class BreviaryService {

    private final PostgresConnection db;

    public BreviaryService(PostgresConnection db) {
        this.db = db;
    }

    public void insertLiturgia(
            String oficioLeitura,
            String laudes,
            String tercia,
            String sexta,
            String noa,
            String vesperas,
            String completas,
            String lingua,
            int cicloLiturgico,
            int dia,
            int mes
    ) {
        db.execUpdate(
                "INSERT INTO liturgia_das_horas (oficio_leitura, laudes, tercia, sexta, noa, vesperas, completas, lingua, ciclo_liturgico, dia, mes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                oficioLeitura,
                laudes,
                tercia,
                sexta,
                noa,
                vesperas,
                completas,
                lingua,
                cicloLiturgico,
                dia,
                mes
        );
    }
}
