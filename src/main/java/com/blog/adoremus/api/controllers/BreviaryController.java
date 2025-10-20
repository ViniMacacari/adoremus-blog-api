package com.blog.adoremus.api.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.adoremus.api.services.BreviaryService;

@RestController
@RequestMapping("/liturgia-das-horas")
public class BreviaryController {

    private final BreviaryService service;

    public BreviaryController(BreviaryService service) {
        this.service = service;
    }

    @PostMapping("/nova")
    public ResponseEntity<Map<String, Object>> novaLiturgia(@RequestBody Map<String, String> body) {
        try {
            String oficioLeitura = body.get("oficio_leitura");
            String laudes = body.get("laudes");
            String tercia = body.get("tercia");
            String sexta = body.get("sexta");
            String noa = body.get("noa");
            String vesperas = body.get("vesperas");
            String completas = body.get("completas");
            String lingua = body.get("lingua");
            int cicloLiturgico = Integer.parseInt(body.get("ciclo_liturgico"));
            int dia = Integer.parseInt(body.get("dia"));
            int mes = Integer.parseInt(body.get("mes"));

            service.insertLiturgia(
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