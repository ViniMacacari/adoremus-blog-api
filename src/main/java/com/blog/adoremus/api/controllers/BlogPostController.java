package com.blog.adoremus.api.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.adoremus.api.services.BlogPostService;

@RestController
@RequestMapping("/blog")
public class BlogPostController {

    private final BlogPostService service;

    public BlogPostController(BlogPostService service) {
        this.service = service;
    }

    @PostMapping("/novo")
    public ResponseEntity<Map<String, Object>> novoPost(@RequestBody Map<String, String> body) {
        try {
            String titulo = body.get("titulo");
            String subtitulo = body.get("subtitulo");
            String conteudo = body.get("conteudo");
            String autor = body.get("autor");

            service.insertPost(titulo, subtitulo, conteudo, autor);

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