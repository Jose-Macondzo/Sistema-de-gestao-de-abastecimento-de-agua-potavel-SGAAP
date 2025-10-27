package com.sgaap.servicos;

import com.sgaap.entidades.Cliente;
import com.sgaap.entidades.Endereco;
import com.sgaap.entidades.Localizacao;
import com.sgaap.repositorios.ClienteRepositorio;
import com.sgaap.repositorios.EnderecoRepositorio;
import com.sgaap.repositorios.LocalizacaoRepositorio;

/**
 * Serviço para gerenciar operações de negócio relacionadas à entidade Cliente no sistema SGAAP.
 * Responsável por cadastrar, atualizar e consultar clientes, integrando com Endereco e Localizacao.
 * Usa repositórios para persistência, mantendo lógica de negócio isolada.
 */
public class ClienteServico {
    private final ClienteRepositorio clienteRepositorio;
    private final EnderecoRepositorio enderecoRepositorio;
    private final LocalizacaoRepositorio localizacaoRepositorio;

    /**
     * Construtor com injeção de dependências dos repositórios necessários.
     * @param clienteRepositorio Repositório para persistência de Cliente
     * @param enderecoRepositorio Repositório para persistência de Endereco
     * @param localizacaoRepositorio Repositório para persistência de Localizacao
     */
    public ClienteServico(ClienteRepositorio clienteRepositorio, EnderecoRepositorio enderecoRepositorio,
                          LocalizacaoRepositorio localizacaoRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
        this.enderecoRepositorio = enderecoRepositorio;
        this.localizacaoRepositorio = localizacaoRepositorio;
    }

    /**
     * Cadastra um novo cliente com endereço e localização.
     * Persiste as entidades em seus respectivos repositórios.
     * @param cliente Cliente a ser cadastrado
     * @param endereco Endereço associado ao cliente
     * @param localizacao Localização associada ao cliente
     * @return Cliente cadastrado
     */
    public Cliente cadastrarCliente(Cliente cliente, Endereco endereco, Localizacao localizacao) {
        if (cliente == null || endereco == null || localizacao == null) {
            throw new IllegalArgumentException("Cliente, endereço ou localização não podem ser nulos");
        }
        // Valida as entidades antes de salvar
        if (!cliente.validarDados() || !endereco.validarEndereco() || !localizacao.validarCoordenadas()) {
            throw new IllegalArgumentException("Dados inválidos para cliente, endereço ou localização");
        }
        // Associa as entidades
        cliente.setEndereco(endereco);
        cliente.setLocalizacao(localizacao);
        endereco.setCliente(cliente);
        // Salva em ordem para garantir consistência
        localizacaoRepositorio.save(localizacao);
        enderecoRepositorio.save(endereco);
        return clienteRepositorio.save(cliente);
    }

    /**
     * Atualiza os dados de um cliente existente, incluindo endereço e localização.
     * @param cliente Cliente atualizado
     * @param endereco Endereço atualizado
     * @param localizacao Localização atualizada
     * @return Cliente atualizado
     */
    public Cliente atualizarCliente(Cliente cliente, Endereco endereco, Localizacao localizacao) {
        if (cliente == null || cliente.getId() == null || endereco == null || localizacao == null) {
            throw new IllegalArgumentException("Cliente, endereço ou localização não podem ser nulos");
        }
        // Verifica se o cliente existe
        if (clienteRepositorio.findById(cliente.getId()) == null) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
        // Valida as entidades
        if (!cliente.validarDados() || !endereco.validarEndereco() || !localizacao.validarCoordenadas()) {
            throw new IllegalArgumentException("Dados inválidos para cliente, endereço ou localização");
        }
        // Associa as entidades
        cliente.setEndereco(endereco);
        cliente.setLocalizacao(localizacao);
        endereco.setCliente(cliente);
        // Atualiza no banco
        localizacaoRepositorio.update(localizacao);
        enderecoRepositorio.update(endereco);
        return clienteRepositorio.update(cliente);
    }

    /**
     * Busca um cliente pelo ID, incluindo endereço e localização.
     * @param id ID do cliente
     * @return Cliente encontrado, ou null se não existir
     */
    public Cliente buscarClientePorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return clienteRepositorio.findById(id);
    }

    /**
     * Busca todos os clientes, incluindo endereço e localização.
     * @return Lista de todos os clientes
     */
    public List<Cliente> buscarTodosClientes() {
        return clienteRepositorio.findAll();
    }
}