package com.sgaap.repositorios;

import com.sgaap.entidades.TaxaInstalacao;
import com.sgaap.repositorios.interfaces.RepositorioInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repositório para a entidade TaxaInstalacao no sistema SGAAP.
 * Implementa operações CRUD usando um HashMap como banco em memória (provisório).
 * Inclui método adicional para buscar taxas por cliente.
 * Responsável apenas por persistência de dados de TaxaInstalacao, sem lógica de negócio.
 */
public class TaxaInstalacaoRepositorio implements RepositorioInterface<TaxaInstalacao, Long> {
    private final Map<Long, TaxaInstalacao> taxas = new HashMap<>(); // Banco em memória
    private static Long nextId = 1L; // Simula auto-incremento de ID

    /**
     * Salva uma nova taxa de instalação ou atualiza uma existente.
     * @param taxa Entidade TaxaInstalacao a ser salva
     * @return TaxaInstalacao salva
     */
    @Override
    public TaxaInstalacao save(TaxaInstalacao taxa) {
        if (taxa == null || !taxa.validarTaxa()) {
            throw new IllegalArgumentException("Taxa de instalação inválida ou dados incompletos");
        }
        if (taxa.getId() == null) {
            taxa.setId(nextId++); // Atribui novo ID
        }
        taxas.put(taxa.getId(), taxa);
        return taxa;
    }

    /**
     * Busca uma taxa de instalação pelo ID.
     * @param id ID da taxa
     * @return TaxaInstalacao encontrada, ou null se não existir
     */
    @Override
    public TaxaInstalacao findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return taxas.get(id);
    }

    /**
     * Busca todas as taxas de instalação.
     * @return Lista de todas as taxas
     */
    @Override
    public List<TaxaInstalacao> findAll() {
        return new ArrayList<>(taxas.values());
    }

    /**
     * Atualiza uma taxa de instalação existente.
     * @param taxa TaxaInstalacao a ser atualizada
     * @return TaxaInstalacao atualizada
     */
    @Override
    public TaxaInstalacao update(TaxaInstalacao taxa) {
        if (taxa == null || taxa.getId() == null || !taxas.containsKey(taxa.getId())) {
            throw new IllegalArgumentException("Taxa de instalação não encontrada ou inválida");
        }
        if (!taxa.validarTaxa()) {
            throw new IllegalArgumentException("Dados da taxa de instalação inválidos");
        }
        taxas.put(taxa.getId(), taxa);
        return taxa;
    }

    /**
     * Deleta uma taxa de instalação pelo ID.
     * @param id ID da taxa a deletar
     */
    @Override
    public void delete(Long id) {
        if (id == null || !taxas.containsKey(id)) {
            throw new IllegalArgumentException("Taxa de instalação não encontrada");
        }
        taxas.remove(id);
    }

    /**
     * Busca todas as taxas de instalação associadas a um cliente específico.
     * @param clienteId ID do cliente
     * @return Lista de taxas do cliente
     */
    public List<TaxaInstalacao> findByClienteId(Long clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo");
        }
        return taxas.values().stream()
                .filter(t -> t.getCliente() != null && clienteId.equals(t.getCliente().getId()))
                .collect(Collectors.toList());
    }
}
