package com.sgaap.repositorios;

import com.sgaap.entidades.Instalacao;
import com.sgaap.repositorios.interfaces.RepositorioInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repositório para a entidade Instalacao no sistema SGAAP.
 * Implementa operações CRUD usando um HashMap como banco em memória (provisório).
 * Inclui método adicional para buscar instalações por abastecimento.
 * Responsável apenas por persistência de dados de Instalacao, sem lógica de negócio.
 */
public class InstalacaoRepositorio implements RepositorioInterface<Instalacao, Long> {
    private final Map<Long, Instalacao> instalacoes = new HashMap<>(); // Banco em memória
    private static Long nextId = 1L; // Simula auto-incremento de ID

    /**
     * Salva uma nova instalação ou atualiza uma existente.
     * @param instalacao Entidade Instalacao a ser salva
     * @return Instalacao salva
     */
    @Override
    public Instalacao save(Instalacao instalacao) {
        if (instalacao == null || !instalacao.validarInstalacao()) {
            throw new IllegalArgumentException("Instalação inválida ou dados incompletos");
        }
        if (instalacao.getId() == null) {
            instalacao.setId(nextId++); // Atribui novo ID
        }
        instalacoes.put(instalacao.getId(), instalacao);
        return instalacao;
    }

    /**
     * Busca uma instalação pelo ID.
     * @param id ID da instalação
     * @return Instalacao encontrada, ou null se não existir
     */
    @Override
    public Instalacao findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return instalacoes.get(id);
    }

    /**
     * Busca todas as instalações.
     * @return Lista de todas as instalações
     */
    @Override
    public List<Instalacao> findAll() {
        return new ArrayList<>(instalacoes.values());
    }

    /**
     * Atualiza uma instalação existente.
     * @param instalacao Instalacao a ser atualizada
     * @return Instalacao atualizada
     */
    @Override
    public Instalacao update(Instalacao instalacao) {
        if (instalacao == null || instalacao.getId() == null || !instalacoes.containsKey(instalacao.getId())) {
            throw new IllegalArgumentException("Instalação não encontrada ou inválida");
        }
        if (!instalacao.validarInstalacao()) {
            throw new IllegalArgumentException("Dados da instalação inválidos");
        }
        instalacoes.put(instalacao.getId(), instalacao);
        return instalacao;
    }

    /**
     * Deleta uma instalação pelo ID.
     * @param id ID da instalação a deletar
     */
    @Override
    public void delete(Long id) {
        if (id == null || !instalacoes.containsKey(id)) {
            throw new IllegalArgumentException("Instalação não encontrada");
        }
        instalacoes.remove(id);
    }

    /**
     * Busca todas as instalações associadas a um abastecimento específico.
     * @param abastecimentoId ID do abastecimento
     * @return Lista de instalações do abastecimento
     */
    public List<Instalacao> findByAbastecimentoId(Long abastecimentoId) {
        if (abastecimentoId == null) {
            throw new IllegalArgumentException("ID do abastecimento não pode ser nulo");
        }
        return instalacoes.values().stream()
                .filter(i -> i.getAbastecimento() != null && abastecimentoId.equals(i.getAbastecimento().getId()))
                .collect(Collectors.toList());
    }
}
