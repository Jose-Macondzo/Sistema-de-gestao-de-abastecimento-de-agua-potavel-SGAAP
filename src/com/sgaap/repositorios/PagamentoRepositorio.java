package com.sgaap.repositorios;

import com.sgaap.entidades.Pagamento;
import com.sgaap.repositorios.interfaces.RepositorioInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repositório para a entidade Pagamento no sistema SGAAP.
 * Implementa operações CRUD usando um HashMap como banco em memória (provisório).
 * Inclui método adicional para buscar pagamentos por fatura.
 * Responsável apenas por persistência de dados de Pagamento, sem lógica de negócio.
 */
public class PagamentoRepositorio implements RepositorioInterface<Pagamento, Long> {
    private final Map<Long, Pagamento> pagamentos = new HashMap<>(); // Banco em memória
    private static Long nextId = 1L; // Simula auto-incremento de ID

    /**
     * Salva um novo pagamento ou atualiza um existente.
     * @param pagamento Entidade Pagamento a ser salva
     * @return Pagamento salvo
     */
    @Override
    public Pagamento save(Pagamento pagamento) {
        if (pagamento == null || !pagamento.validarPagamento()) {
            throw new IllegalArgumentException("Pagamento inválido ou dados incompletos");
        }
        if (pagamento.getId() == null) {
            pagamento.setId(nextId++); // Atribui novo ID
        }
        pagamentos.put(pagamento.getId(), pagamento);
        return pagamento;
    }

    /**
     * Busca um pagamento pelo ID.
     * @param id ID do pagamento
     * @return Pagamento encontrado, ou null se não existir
     */
    @Override
    public Pagamento findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return pagamentos.get(id);
    }

    /**
     * Busca todos os pagamentos.
     * @return Lista de todos os pagamentos
     */
    @Override
    public List<Pagamento> findAll() {
        return new ArrayList<>(pagamentos.values());
    }

    /**
     * Atualiza um pagamento existente.
     * @param pagamento Pagamento a ser atualizado
     * @return Pagamento atualizado
     */
    @Override
    public Pagamento update(Pagamento pagamento) {
        if (pagamento == null || pagamento.getId() == null || !pagamentos.containsKey(pagamento.getId())) {
            throw new IllegalArgumentException("Pagamento não encontrado ou inválido");
        }
        if (!pagamento.validarPagamento()) {
            throw new IllegalArgumentException("Dados do pagamento inválidos");
        }
        pagamentos.put(pagamento.getId(), pagamento);
        return pagamento;
    }

    /**
     * Deleta um pagamento pelo ID.
     * @param id ID do pagamento a deletar
     */
    @Override
    public void delete(Long id) {
        if (id == null || !pagamentos.containsKey(id)) {
            throw new IllegalArgumentException("Pagamento não encontrado");
        }
        pagamentos.remove(id);
    }

    /**
     * Busca todos os pagamentos associados a uma fatura específica.
     * @param faturaId ID da fatura
     * @return Lista de pagamentos da fatura
     */
    public List<Pagamento> findByFaturaId(Long faturaId) {
        if (faturaId == null) {
            throw new IllegalArgumentException("ID da fatura não pode ser nulo");
        }
        return pagamentos.values().stream()
                .filter(p -> p.getFatura() != null && faturaId.equals(p.getFatura().getId()))
                .collect(Collectors.toList());
    }
}
