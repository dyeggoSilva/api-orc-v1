package com.example.orcamento.service;

import com.example.orcamento.exception.OrcamentoNaoEncontradoException;
import com.example.orcamento.model.Orcamento;
import com.example.orcamento.repository.OrcamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Service
public class OrcamentoService {

    private final OrcamentoRepository repository;
    private final PdfGeneratorService pdfGeneratorService;

    public OrcamentoService(OrcamentoRepository repository, PdfGeneratorService pdfGeneratorService) {
        this.repository = repository;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    public Orcamento criar(Orcamento orcamento) {
        orcamento.setId(UUID.randomUUID().toString());
        orcamento.setDataCriacao(LocalDate.now());
        return repository.save(orcamento);
    }

    public Orcamento buscarPorId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrcamentoNaoEncontradoException(id));
    }

    public Collection<Orcamento> listarTodos() {
        return repository.findAll();
    }

    public byte[] gerarPdf(String id) {
        Orcamento orcamento = buscarPorId(id);
        return pdfGeneratorService.gerarPdf(orcamento);
    }
}
