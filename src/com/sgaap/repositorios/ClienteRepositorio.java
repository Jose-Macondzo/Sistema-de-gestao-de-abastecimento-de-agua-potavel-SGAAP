package com.sgaap.repositorios;

import com.sgaap.entidades.Cliente;
import com.sgaap.repositorios.interfaces.RepositorioInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositório para a entidade Cliente no sistema SGAAP.
 * Implementa operações CRUD usando um HashMap como banco em memória (provisório).
 * Responsável apenas por persistência de dados de Cliente, sem lógica de negócio.
 */
public class ClienteRepositorio implements RepositorioInterface<Cliente, Long> {
    private final Map<Long, Cliente> clientes = new HashMap<>(); // Banco em memória
    private static Long nextId = 1L; // Simula auto-incremento de ID

    /**
     * Salva um novo cliente ou atualiza um existente.
     * @param cliente Entidade Cliente a ser salva
     * @return Cliente salvo
     */
    @Override
    public Cliente save(Cliente cliente) {
        if (cliente == null || !cliente.validarDados()) {
            throw new IllegalArgumentException("Cliente inválido ou dados incompletos");
        }
        if (cliente.getId() == null) {
            cliente.setId(nextId++); // Atribui novo ID
        }
        clientes.put(cliente.getId(), cliente);
        return cliente;
    }

    /**
     * Busca um cliente pelo ID.
     * @param id ID do cliente
     * @return Cliente encontrado, ou null se não existir
     */
    @Override
    public Cliente findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return clientes.get(id);
    }

    /**
     * Busca todos os clientes.
     * @return Lista de todos os clientes
     */
    @Override
    public List<Cliente> findAll() {
        return new ArrayList<>(clientes.values());
    }

    /**
     * Atualiza um cliente existente.
     * @param cliente Cliente a ser atualizado
     * @return Cliente atualizado
     */
    @Override
    public Cliente update(Cliente cliente) {
        if (cliente == null || cliente.getId() == null || !clientes.containsKey(cliente.getId())) {
            throw new IllegalArgumentException("Cliente não encontrado ou inválido");
        }
        if (!cliente.validarDados()) {
            throw new IllegalArgumentException("Dados do cliente inválidos");
        }
        clientes.put(cliente.getId(), cliente);
        return cliente;
    }

    /**
     * Deleta um cliente pelo ID.
     * @param id ID do cliente a deletar
     */
    @Override
    public void delete(Long id) {
        if (id == null || !clientes.containsKey(id)) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
        clientes.remove(id);
    }
}