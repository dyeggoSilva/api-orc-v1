package com.example.orcamento.controller;

import com.example.orcamento.model.Orcamento;
import com.example.orcamento.service.OrcamentoService;
import jakarta.validation.Valid;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/orcamentos")
public class OrcamentoController {

    private final OrcamentoService service;

    public OrcamentoController(OrcamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Orcamento> criar(@Valid @RequestBody Orcamento orcamento) {
        Orcamento criado = service.criar(orcamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public ResponseEntity<Collection<Orcamento>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orcamento> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> gerarPdf(@PathVariable String id) {
        byte[] pdf = service.gerarPdf(id);

        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename("orcamento-" + id + ".pdf")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(contentDisposition);

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }
}
