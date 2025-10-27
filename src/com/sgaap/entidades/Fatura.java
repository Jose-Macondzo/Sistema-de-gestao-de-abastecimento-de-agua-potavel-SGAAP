package com.sgaap.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidade que representa uma fatura no sistema SGAAP, contendo valor base, data de vencimento e multas.
 * Associada a Cliente e LeituraContador; suporta múltiplos Pagamentos.
 * Responsável apenas por armazenar e validar dados da fatura, sem lógica de cálculo externa.
 */
public class Fatura implements Serializable {
    private Long id;                   // Identificador único para persistência
    private BigDecimal valorBase;      // Valor calculado com base no consumo (m³)
    private LocalDate dataVencimento;   // Data de vencimento da fatura
    private BigDecimal multasAplicadas;// Total de multas aplicadas (ex.: 10% após 15 dias)
    private Cliente cliente;           // Cliente associado à fatura
    private LeituraContador leitura;   // Leitura do contador que gerou a fatura

    // Construtor padrão (necessário para frameworks como JPA/Hibernate)
    public Fatura() {
    }

    // Construtor completo para criação explícita
    public Fatura(Long id, BigDecimal valorBase, LocalDate dataVencimento, BigDecimal multasAplicadas,
                  Cliente cliente, LeituraContador leitura) {
        this.id = id;
        this.valorBase = valorBase;
        this.dataVencimento = dataVencimento;
        this.multasAplicadas = multasAplicadas;
        this.cliente = cliente;
        this.leitura = leitura;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorBase() {
        return valorBase;
    }

    public void setValorBase(BigDecimal valorBase) {
        this.valorBase = valorBase;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getMultasAplicadas() {
        return multasAplicadas;
    }

    public void setMultasAplicadas(BigDecimal multasAplicadas) {
        this.multasAplicadas = multasAplicadas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LeituraContador getLeitura() {
        return leitura;
    }

    public void setLeitura(LeituraContador leitura) {
        this.leitura = leitura;
    }

    /**
     * Valida os dados da fatura: valor base não-negativo, data de vencimento e cliente não nulos.
     * Método curto para fácil teste e manutenção.
     * @return true se válido, false caso contrário
     */
    public boolean validarFatura() {
        if (valorBase == null || valorBase.compareTo(BigDecimal.ZERO) < 0) return false;
        if (dataVencimento == null) return false;
        if (multasAplicadas == null || multasAplicadas.compareTo(BigDecimal.ZERO) < 0) return false;
        if (cliente == null || !cliente.validarDados()) return false;
        if (leitura == null) return false;
        return true;
    }

    /**
     * Verifica se a fatura está em dívida (não totalmente paga, considerando valor base + multas).
     * Método simples, com lógica de pagamento em FaturaServico.
     * @return true se em dívida, false caso contrário
     */
    public boolean isEmDivida() {
        return multasAplicadas.compareTo(BigDecimal.ZERO) > 0 || valorBase.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Formata os dados da fatura para exibição em relatórios, UI ou PDFs.
     * Reutilizável, chamando formatação do Cliente.
     * @return String formatada (ex.: "Fatura #123, Cliente: João Silva, Valor: 100.00, Vencimento: 2025-10-27")
     */
    public String toStringFormatado() {
        return String.format("Fatura #%d, Cliente: %s, Valor: %.2f, Vencimento: %s",
                id, cliente.getNomeCompleto(), valorBase.add(multasAplicadas), dataVencimento);
    }

    @Override
    public String toString() {
        return "Fatura{id=" + id + ", valorBase=" + valorBase + ", dataVencimento=" + dataVencimento +
                ", multasAplicadas=" + multasAplicadas + ", cliente=" + cliente + ", leitura=" + leitura + "}";
    }
}