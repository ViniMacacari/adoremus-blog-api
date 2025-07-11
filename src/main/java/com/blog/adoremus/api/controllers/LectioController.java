package com.blog.adoremus.api.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.adoremus.api.services.LectioService;

@RestController
@RequestMapping("/lectio")
public class LectioController {

    private final LectioService service;

    public LectioController(LectioService service) {
        this.service = service;
    }

    @PostMapping("/nova")
    public ResponseEntity<Map<String, Object>> novaLectio(@RequestBody Map<String, String> body) {
        try {
            String livro = body.get("livro");
            String passagem = body.get("passagem");
            String texto = body.get("texto");
            int biblia = Integer.parseInt(body.get("biblia"));
            String conteudo = body.get("conteudo");

            service.insertLectio(livro, passagem, texto, biblia, conteudo);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
