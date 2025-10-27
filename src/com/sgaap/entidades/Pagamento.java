package com.sgaap.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidade que representa um pagamento no sistema SGAAP, contendo valor, data e método de pagamento.
 * Associada a uma Fatura; métodos são apenas descritivos (cartão, Mpesa, Emola, numerário).
 * Responsável apenas por armazenar e validar dados do pagamento, sem lógica de processamento externa.
 */
public class Pagamento implements Serializable {
    private Long id;                 // Identificador único para persistência
    private BigDecimal valor;        // Valor do pagamento
    private LocalDate dataPagamento; // Data do pagamento (ex.: 2025-09-27)
    private String metodo;           // Método de pagamento (ex.: "Cartão", "Mpesa", "Emola", "Numerário")
    private Fatura fatura;           // Fatura associada ao pagamento

    // Construtor padrão (necessário para frameworks como JPA/Hibernate)
    public Pagamento() {
    }

    // Construtor completo para criação explícita
    public Pagamento(Long id, BigDecimal valor, LocalDate dataPagamento, String metodo, Fatura fatura) {
        this.id = id;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.metodo = metodo;
        this.fatura = fatura;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Fatura getFatura() {
        return fatura;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
    }

    /**
     * Valida os dados do pagamento: valor não-negativo, data e fatura não nulos, método válido.
     * Método curto para fácil teste e manutenção.
     * @return true se válido, false caso contrário
     */
    public boolean validarPagamento() {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) return false;
        if (dataPagamento == null) return false;
        if (metodo == null || !metodo.matches("Cartão|Mpesa|Emola|Numerário")) return false;
        if (fatura == null || !fatura.validarFatura()) return false;
        return true;
    }

    /**
     * Formata os dados do pagamento para exibição em relatórios, UI ou PDFs.
     * Reutilizável, chamando formatação da Fatura.
     * @return String formatada (ex.: "Pagamento de 100.00 em 2025-09-27 via Mpesa, Fatura #123")
     */
    public String toStringFormatado() {
        return String.format("Pagamento de %.2f em %s via %s, Fatura #%d",
                valor, dataPagamento, metodo, fatura.getId());
    }

    @Override
    public String toString() {
        return "Pagamento{id=" + id + ", valor=" + valor + ", dataPagamento=" + dataPagamento +
                ", metodo='" + metodo + "', fatura=" + fatura + "}";
    }
}