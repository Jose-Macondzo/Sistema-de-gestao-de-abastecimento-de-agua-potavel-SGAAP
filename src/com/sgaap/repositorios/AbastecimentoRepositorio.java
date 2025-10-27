package com.sgaap.repositorios;

import com.sgaap.entidades.Abastecimento;
import com.sgaap.repositorios.interfaces.RepositorioInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositório para a entidade Abastecimento no sistema SGAAP.
 * Implementa operações CRUD usando um HashMap como banco em memória (provisório).
 * Responsável apenas por persistência de dados de Abastecimento, sem lógica de negócio.
 */
public class AbastecimentoRepositorio implements RepositorioInterface<Abastecimento, Long> {
    private final Map<Long, Abastecimento> abastecimentos = new HashMap<>(); // Banco em memória
    private static Long nextId = 1L; // Simula auto-incremento de ID

    /**
     * Salva um novo abastecimento ou atualiza um existente.
     * @param abastecimento Entidade Abastecimento a ser salva
     * @return Abastecimento salvo
     */
    @Override
    public Abastecimento save(Abastecimento abastecimento) {
        if (abastecimento == null || !abastecimento.validarAbastecimento()) {
            throw new IllegalArgumentException("Abastecimento inválido ou dados incompletos");
        }
        if (abastecimento.getId() == null) {
            abastecimento.setId(nextId++); // Atribui novo ID
        }
        abastecimentos.put(abastecimento.getId(), abastecimento);
        return abastecimento;
    }

    /**
     * Busca um abastecimento pelo ID.
     * @param id ID do abastecimento
     * @return Abastecimento encontrado, ou null se não existir
     */
    @Override
    public Abastecimento findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return abastecimentos.get(id);
    }

    /**
     * Busca todos os abastecimentos.
     * @return Lista de todos os abastecimentos
     */
    @Override
    public List<Abastecimento> findAll() {
        return new ArrayList<>(abastecimentos.values());
    }

    /**
     * Atualiza um abastecimento existente.
     * @param abastecimento Abastecimento a ser atualizado
     * @return Abastecimento atualizado
     */
    @Override
    public Abastecimento update(Abastecimento abastecimento) {
        if (abastecimento == null || abastecimento.getId() == null || !abastecimentos.containsKey(abastecimento.getId())) {
            throw new IllegalArgumentException("Abastecimento não encontrado ou inválido");
        }
        if (!abastecimento.validarAbastecimento()) {
            throw new IllegalArgumentException("Dados do abastecimento inválidos");
        }
        abastecimentos.put(abastecimento.getId(), abastecimento);
        return abastecimento;
    }

    /**
     * Deleta um abastecimento pelo ID.
     * @param id ID do abastecimento a deletar
     */
    @Override
    public void delete(Long id) {
        if (id == null || !abastecimentos.containsKey(id)) {
            throw new IllegalArgumentException("Abastecimento não encontrado");
        }
        abastecimentos.remove(id);
    }
}
