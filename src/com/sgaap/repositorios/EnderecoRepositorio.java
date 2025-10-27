package com.sgaap.repositorios;

import com.sgaap.entidades.Endereco;
import com.sgaap.repositorios.interfaces.RepositorioInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repositório para a entidade Endereco no sistema SGAAP.
 * Implementa operações CRUD usando um HashMap como banco em memória (provisório).
 * Inclui método adicional para buscar endereços por cliente.
 * Responsável apenas por persistência de dados de Endereco, sem lógica de negócio.
 */
public class EnderecoRepositorio implements RepositorioInterface<Endereco, Long> {
    private final Map<Long, Endereco> enderecos = new HashMap<>(); // Banco em memória
    private static Long nextId = 1L; // Simula auto-incremento de ID

    /**
     * Salva um novo endereço ou atualiza um existente.
     * @param endereco Entidade Endereco a ser salva
     * @return Endereco salvo
     */
    @Override
    public Endereco save(Endereco endereco) {
        if (endereco == null || !endereco.validarEndereco()) {
            throw new IllegalArgumentException("Endereço inválido ou dados incompletos");
        }
        if (endereco.getId() == null) {
            endereco.setId(nextId++); // Atribui novo ID
        }
        enderecos.put(endereco.getId(), endereco);
        return endereco;
    }

    /**
     * Busca um endereço pelo ID.
     * @param id ID do endereço
     * @return Endereco encontrado, ou null se não existir
     */
    @Override
    public Endereco findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return enderecos.get(id);
    }

    /**
     * Busca todos os endereços.
     * @return Lista de todos os endereços
     */
    @Override
    public List<Endereco> findAll() {
        return new ArrayList<>(enderecos.values());
    }

    /**
     * Atualiza um endereço existente.
     * @param endereco Endereco a ser atualizado
     * @return Endereco atualizado
     */
    @Override
    public Endereco update(Endereco endereco) {
        if (endereco == null || endereco.getId() == null || !enderecos.containsKey(endereco.getId())) {
            throw new IllegalArgumentException("Endereço não encontrado ou inválido");
        }
        if (!endereco.validarEndereco()) {
            throw new IllegalArgumentException("Dados do endereço inválidos");
        }
        enderecos.put(endereco.getId(), endereco);
        return endereco;
    }

    /**
     * Deleta um endereço pelo ID.
     * @param id ID do endereço a deletar
     */
    @Override
    public void delete(Long id) {
        if (id == null || !enderecos.containsKey(id)) {
            throw new IllegalArgumentException("Endereço não encontrado");
        }
        enderecos.remove(id);
    }

    /**
     * Busca todos os endereços associados a um cliente específico.
     * @param clienteId ID do cliente
     * @return Lista de endereços do cliente
     */
    public List<Endereco> findByClienteId(Long clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo");
        }
        return enderecos.values().stream()
                .filter(e -> e.getCliente() != null && clienteId.equals(e.getCliente().getId()))
                .collect(Collectors.toList());
    }
}
