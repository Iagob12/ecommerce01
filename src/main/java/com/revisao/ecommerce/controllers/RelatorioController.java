package com.revisao.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revisao.ecommerce.services.RelatorioService;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    RelatorioService relatorio;

    @GetMapping
    public ResponseEntity<String> gerarRelatorio(@RequestParam String caminho) {
        if (caminho == null || caminho.isEmpty()) {
            return ResponseEntity.badRequest().body("O parâmetro 'caminho' é obrigatório e não pode estar vazio.");
        }
        relatorio.gerarRelatorioServicePDF(caminho);
        return ResponseEntity.ok("Relatório gerado com sucesso em: " + caminho);
    }
}