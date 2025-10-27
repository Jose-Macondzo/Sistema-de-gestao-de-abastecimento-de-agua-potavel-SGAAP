package com.sgaap.servicos;

import com.sgaap.entidades.Fatura;
import com.sgaap.entidades.Pagamento;
import com.sgaap.repositorios.FaturaRepositorio;
import com.sgaap.repositorios.PagamentoRepositorio;
import com.sgaap.servicos.interfaces.ServicoInterface;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar operações de negócio relacionadas à entidade Pagamento no sistema SGAAP.
 * Responsável por processar pagamentos, atualizar saldos de faturas e consultar pagamentos.
 * Usa repositórios para persistência, mantendo lógica de negócio isolada.
 */
public class PagamentoServico implements ServicoInterface<Pagamento, Long> {
    private final PagamentoRepositorio pagamentoRepositorio;
    private final FaturaRepositorio faturaRepositorio;

    /**
     * Construtor com injeção de dependências dos repositórios necessários.
     * @param pagamentoRepositorio Repositório para persistência de Pagamento
     * @param faturaRepositorio Repositório para persistência de Fatura
     */
    public PagamentoServico(PagamentoRepositorio pagamentoRepositorio, FaturaRepositorio faturaRepositorio) {
        this.pagamentoRepositorio = pagamentoRepositorio;
        this.faturaRepositorio = faturaRepositorio;
    }

    /**
     * Processa um pagamento para uma fatura, atualizando seu saldo.
     * Valida o método de pagamento e verifica se o valor cobre o saldo devido.
     * @param faturaId ID da fatura
     * @param valor Valor do pagamento
     * @param metodo Metodo de pagamento (CARTAO, MPESA, EMOLA, NUMERARIO)
     * @return Pagamento processado
     */
    public Pagamento processarPagamento(Long faturaId, BigDecimal valor, Pagamento metodo) {
        if (faturaId == null || valor == null || metodo == null) {
            throw new IllegalArgumentException("Fatura, valor ou método de pagamento não podem ser nulos");
        }
        Fatura fatura = faturaRepositorio.findById(faturaId);
        if (fatura == null) {
            throw new IllegalArgumentException("Fatura não encontrada");
        }
        BigDecimal saldoDevido = fatura.getValorBase().add(fatura.getMultasAplicadas());
        if (valor.compareTo(saldoDevido) < 0) {
            throw new IllegalArgumentException("Valor do pagamento insuficiente para cobrir o saldo");
        }
        Pagamento pagamento = new Pagamento();
        pagamento.setFatura(fatura);
        pagamento.setValor(valor);
        //pagamento.setMetodo(metodo);
        pagamento.setDataPagamento(LocalDate.now());
        //fatura.setSaldoDevido(saldoDevido.subtract(valor));
        faturaRepositorio.update(fatura);
        return cadastrar(pagamento);
    }

    /**
     * Cadastra um novo pagamento.
     * @param pagamento Pagamento a ser cadastrado
     * @return Pagamento cadastrado
     */
    @Override
    public Pagamento cadastrar(Pagamento pagamento) {
        if (pagamento == null || !pagamento.validarPagamento()) {
            throw new IllegalArgumentException("Pagamento inválido ou dados incompletos");
        }
        return pagamentoRepositorio.save(pagamento);
    }

    /**
     * Atualiza um pagamento existente.
     * @param pagamento Pagamento a ser atualizado
     * @return Pagamento atualizado
     */
    @Override
    public Pagamento atualizar(Pagamento pagamento) {
        if (pagamento == null || pagamento.getId() == null) {
            throw new IllegalArgumentException("Pagamento inválido ou ID nulo");
        }
        if (!pagamento.validarPagamento()) {
            throw new IllegalArgumentException("Dados do pagamento inválidos");
        }
        return pagamentoRepositorio.update(pagamento);
    }

    /**
     * Busca um pagamento pelo ID.
     * @param id ID do pagamento
     * @return Pagamento encontrado, ou null se não existir
     */
    @Override
    public Pagamento buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return pagamentoRepositorio.findById(id);
    }

    /**
     * Busca todos os pagamentos.
     * @return Lista de todos os pagamentos
     */
    @Override
    public List<Pagamento> buscarTodos() {
        return pagamentoRepositorio.findAll();
    }

    /**
     * Busca todos os pagamentos associados a um cliente, usando o cliente da fatura.
     * @param clienteId ID do cliente
     * @return Lista de pagamentos do cliente
     */
    public List<Pagamento> buscarPorClienteId(Long clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo");
        }
        return pagamentoRepositorio.findAll().stream()
                .filter(p -> p.getFatura() != null && p.getFatura().getCliente() != null &&
                        clienteId.equals(p.getFatura().getCliente().getId()))
                .collect(Collectors.toList());
    }
}