package com.sgaap.repositorios;

import com.sgaap.entidades.Contrato;
import com.sgaap.entidades.Contrato.StatusContrato;
import com.sgaap.repositorios.interfaces.RepositorioInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repositório para a entidade Contrato no sistema SGAAP.
 * Implementa operações CRUD usando um HashMap como banco em memória (provisório).
 * Inclui método adicional para buscar contratos por status (ativo/encerrado).
 * Responsável apenas por persistência de dados de Contrato, sem lógica de negócio.
 */
public class ContratoRepositorio implements RepositorioInterface<Contrato, Long> {
    private final Map<Long, Contrato> contratos = new HashMap<>(); // Banco em memória
    private static Long nextId = 1L; // Simula auto-incremento de ID

    /**
     * Salva um novo contrato ou atualiza um existente.
     * @param contrato Entidade Contrato a ser salva
     * @return Contrato salvo
     */
    @Override
    public Contrato save(Contrato contrato) {
        if (contrato == null || !contrato.validarContrato()) {
            throw new IllegalArgumentException("Contrato inválido ou dados incompletos");
        }
        if (contrato.getId() == null) {
            contrato.setId(nextId++); // Atribui novo ID
        }
        contratos.put(contrato.getId(), contrato);
        return contrato;
    }

    /**
     * Busca um contrato pelo ID.
     * @param id ID do contrato
     * @return Contrato encontrado, ou null se não existir
     */
    @Override
    public Contrato findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return contratos.get(id);
    }

    /**
     * Busca todos os contratos.
     * @return Lista de todos os contratos
     */
    @Override
    public List<Contrato> findAll() {
        return new ArrayList<>(contratos.values());
    }

    /**
     * Atualiza um contrato existente.
     * @param contrato Contrato a ser atualizado
     * @return Contrato atualizado
     */
    @Override
    public Contrato update(Contrato contrato) {
        if (contrato == null || contrato.getId() == null || !contratos.containsKey(contrato.getId())) {
            throw new IllegalArgumentException("Contrato não encontrado ou inválido");
        }
        if (!contrato.validarContrato()) {
            throw new IllegalArgumentException("Dados do contrato inválidos");
        }
        contratos.put(contrato.getId(), contrato);
        return contrato;
    }

    /**
     * Deleta um contrato pelo ID.
     * @param id ID do contrato a deletar
     */
    @Override
    public void delete(Long id) {
        if (id == null || !contratos.containsKey(id)) {
            throw new IllegalArgumentException("Contrato não encontrado");
        }
        contratos.remove(id);
    }

    /**
     * Busca contratos por status (ativo ou encerrado).
     * @param status Status do contrato a filtrar
     * @return Lista de contratos com o status especificado
     */
    public List<Contrato> findByStatus(StatusContrato status) {
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }
        return contratos.values().stream()
                .filter(c -> status.equals(c.getStatus()))
                .collect(Collectors.toList());
    }
}
