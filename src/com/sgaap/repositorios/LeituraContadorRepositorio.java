package com.sgaap.repositorios;

import com.sgaap.entidades.LeituraContador;
import com.sgaap.repositorios.interfaces.RepositorioInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repositório para a entidade LeituraContador no sistema SGAAP.
 * Implementa operações CRUD usando um HashMap como banco em memória (provisório).
 * Inclui método adicional para buscar leituras por instalação.
 * Responsável apenas por persistência de dados de LeituraContador, sem lógica de negócio.
 */
public class LeituraContadorRepositorio implements RepositorioInterface<LeituraContador, Long> {
    private final Map<Long, LeituraContador> leituras = new HashMap<>(); // Banco em memória
    private static Long nextId = 1L; // Simula auto-incremento de ID

    /**
     * Salva uma nova leitura do contador ou atualiza uma existente.
     * @param leitura Entidade LeituraContador a ser salva
     * @return LeituraContador salva
     */
    @Override
    public LeituraContador save(LeituraContador leitura) {
        if (leitura == null || !leitura.validarLeitura()) {
            throw new IllegalArgumentException("Leitura do contador inválida ou dados incompletos");
        }
        if (leitura.getId() == null) {
            leitura.setId(nextId++); // Atribui novo ID
        }
        leituras.put(leitura.getId(), leitura);
        return leitura;
    }

    /**
     * Busca uma leitura do contador pelo ID.
     * @param id ID da leitura
     * @return LeituraContador encontrada, ou null se não existir
     */
    @Override
    public LeituraContador findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return leituras.get(id);
    }

    /**
     * Busca todas as leituras do contador.
     * @return Lista de todas as leituras
     */
    @Override
    public List<LeituraContador> findAll() {
        return new ArrayList<>(leituras.values());
    }

    /**
     * Atualiza uma leitura do contador existente.
     * @param leitura LeituraContador a ser atualizada
     * @return LeituraContador atualizada
     */
    @Override
    public LeituraContador update(LeituraContador leitura) {
        if (leitura == null || leitura.getId() == null || !leituras.containsKey(leitura.getId())) {
            throw new IllegalArgumentException("Leitura do contador não encontrada ou inválida");
        }
        if (!leitura.validarLeitura()) {
            throw new IllegalArgumentException("Dados da leitura inválidos");
        }
        leituras.put(leitura.getId(), leitura);
        return leitura;
    }

    /**
     * Deleta uma leitura do contador pelo ID.
     * @param id ID da leitura a deletar
     */
    @Override
    public void delete(Long id) {
        if (id == null || !leituras.containsKey(id)) {
            throw new IllegalArgumentException("Leitura do contador não encontrada");
        }
        leituras.remove(id);
    }

    /**
     * Busca todas as leituras associadas a uma instalação específica.
     * @param instalacaoId ID da instalação
     * @return Lista de leituras da instalação
     */
    public List<LeituraContador> findByInstalacaoId(Long instalacaoId) {
        if (instalacaoId == null) {
            throw new IllegalArgumentException("ID da instalação não pode ser nulo");
        }
        return leituras.values().stream()
                .filter(l -> l.getInstalacao() != null && instalacaoId.equals(l.getInstalacao().getId()))
                .collect(Collectors.toList());
    }
}
