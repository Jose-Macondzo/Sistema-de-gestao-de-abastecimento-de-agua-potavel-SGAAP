package com.sgaap.repositorios;

import com.sgaap.entidades.Fatura;
import com.sgaap.repositorios.interfaces.RepositorioInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repositório para a entidade Fatura no sistema SGAAP.
 * Implementa operações CRUD usando um HashMap como banco em memória (provisório).
 * Inclui método adicional para buscar faturas em dívida.
 * Responsável apenas por persistência de dados de Fatura, sem lógica de negócio.
 */
public class FaturaRepositorio implements RepositorioInterface<Fatura, Long> {
    private final Map<Long, Fatura> faturas = new HashMap<>(); // Banco em memória
    private static Long nextId = 1L; // Simula auto-incremento de ID

    /**
     * Salva uma nova fatura ou atualiza uma existente.
     * @param fatura Entidade Fatura a ser salva
     * @return Fatura salva
     */
    @Override
    public Fatura save(Fatura fatura) {
        if (fatura == null || !fatura.validarFatura()) {
            throw new IllegalArgumentException("Fatura inválida ou dados incompletos");
        }
        if (fatura.getId() == null) {
            fatura.setId(nextId++); // Atribui novo ID
        }
        faturas.put(fatura.getId(), fatura);
        return fatura;
    }

    /**
     * Busca uma fatura pelo ID.
     * @param id ID da fatura
     * @return Fatura encontrada, ou null se não existir
     */
    @Override
    public Fatura findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return faturas.get(id);
    }

    /**
     * Busca todas as faturas.
     * @return Lista de todas as faturas
     */
    @Override
    public List<Fatura> findAll() {
        return new ArrayList<>(faturas.values());
    }

    /**
     * Atualiza uma fatura existente.
     * @param fatura Fatura a ser atualizada
     * @return Fatura atualizada
     */
    @Override
    public Fatura update(Fatura fatura) {
        if (fatura == null || fatura.getId() == null || !faturas.containsKey(fatura.getId())) {
            throw new IllegalArgumentException("Fatura não encontrada ou inválida");
        }
        if (!fatura.validarFatura()) {
            throw new IllegalArgumentException("Dados da fatura inválidos");
        }
        faturas.put(fatura.getId(), fatura);
        return fatura;
    }

    /**
     * Deleta uma fatura pelo ID.
     * @param id ID da fatura a deletar
     */
    @Override
    public void delete(Long id) {
        if (id == null || !faturas.containsKey(id)) {
            throw new IllegalArgumentException("Fatura não encontrada");
        }
        faturas.remove(id);
    }

    /**
     * Busca todas as faturas em dívida (não totalmente pagas).
     * @return Lista de faturas em dívida
     */
    public List<Fatura> findFaturasEmDivida() {
        return faturas.values().stream()
                .filter(Fatura::isEmDivida)
                .collect(Collectors.toList());
    }
}