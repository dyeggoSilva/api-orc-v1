package com.example.orcamento.repository;

import com.example.orcamento.model.Orcamento;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repositorio simples em memoria. Os dados sao perdidos quando a
 * aplicacao e reiniciada, ja que nao ha persistencia em banco de dados.
 */
@Repository
public class OrcamentoRepository {

    private final Map<String, Orcamento> database = new ConcurrentHashMap<>();

    public Orcamento save(Orcamento orcamento) {
        database.put(orcamento.getId(), orcamento);
        return orcamento;
    }

    public Optional<Orcamento> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    public Collection<Orcamento> findAll() {
        return database.values();
    }

    public boolean existsById(String id) {
        return database.containsKey(id);
    }

    public void deleteById(String id) {
        database.remove(id);
    }
}
