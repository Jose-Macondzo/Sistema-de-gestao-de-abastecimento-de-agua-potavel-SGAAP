package com.sgaap.repositorios;

import com.sgaap.entidades.Localizacao;
import com.sgaap.repositorios.interfaces.RepositorioInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositório para a entidade Localizacao no sistema SGAAP.
 * Implementa operações CRUD usando um HashMap como banco em memória (provisório).
 * Responsável apenas por persistência de dados de Localizacao, sem lógica de negócio.
 */
public class LocalizacaoRepositorio implements RepositorioInterface<Localizacao, Long> {
    private final Map<Long, Localizacao> localizacoes = new HashMap<>(); // Banco em memória
    private static Long nextId = 1L; // Simula auto-incremento de ID

    /**
     * Salva uma nova localização ou atualiza uma existente.
     * @param localizacao Entidade Localizacao a ser salva
     * @return Localizacao salva
     */
    @Override
    public Localizacao save(Localizacao localizacao) {
        if (localizacao == null || !localizacao.validarCoordenadas()) {
            throw new IllegalArgumentException("Localização inválida ou dados incompletos");
        }
        if (localizacao.getId() == null) {
            localizacao.setId(nextId++); // Atribui novo ID
        }
        localizacoes.put(localizacao.getId(), localizacao);
        return localizacao;
    }

    /**
     * Busca uma localização pelo ID.
     * @param id ID da localização
     * @return Localizacao encontrada, ou null se não existir
     */
    @Override
    public Localizacao findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return localizacoes.get(id);
    }

    /**
     * Busca todas as localizações.
     * @return Lista de todas as localizações
     */
    @Override
    public List<Localizacao> findAll() {
        return new ArrayList<>(localizacoes.values());
    }

    /**
     * Atualiza uma localização existente.
     * @param localizacao Localizacao a ser atualizada
     * @return Localizacao atualizada
     */
    @Override
    public Localizacao update(Localizacao localizacao) {
        if (localizacao == null || localizacao.getId() == null || !localizacoes.containsKey(localizacao.getId())) {
            throw new IllegalArgumentException("Localização não encontrada ou inválida");
        }
        if (!localizacao.validarCoordenadas()) {
            throw new IllegalArgumentException("Dados da localização inválidos");
        }
        localizacoes.put(localizacao.getId(), localizacao);
        return localizacao;
    }

    /**
     * Deleta uma localização pelo ID.
     * @param id ID da localização a deletar
     */
    @Override
    public void delete(Long id) {
        if (id == null || !localizacoes.containsKey(id)) {
            throw new IllegalArgumentException("Localização não encontrada");
        }
        localizacoes.remove(id);
    }
}
