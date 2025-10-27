package com.sgaap.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entidade que representa a taxa de instalação no sistema SGAAP, contendo valor e tipo.
 * Associada a Cliente e Contrato para ativação do fornecimento de água.
 * Responsável apenas por armazenar e validar dados da taxa, sem lógica de aplicação externa.
 */
public class TaxaInstalacao implements Serializable {
    private Long id;               // Identificador único para persistência
    private BigDecimal valor;      // Valor da taxa de instalação
    private String tipo;           // Tipo da taxa (ex.: "Inicial", "Manutenção")
    private Cliente cliente;       // Cliente associado à taxa
    private Contrato contrato;     // Contrato associado à taxa (ativação do fornecimento)

    // Construtor padrão (necessário para frameworks como JPA/Hibernate)
    public TaxaInstalacao() {
    }

    // Construtor completo para criação explícita
    public TaxaInstalacao(Long id, BigDecimal valor, String tipo, Cliente cliente, Contrato contrato) {
        this.id = id;
        this.valor = valor;
        this.tipo = tipo;
        this.cliente = cliente;
        this.contrato = contrato;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    /**
     * Valida os dados da taxa: valor não-negativo, tipo e cliente não nulos.
     * Método curto para fácil teste e manutenção.
     * @return true se válido, false caso contrário
     */
    public boolean validarTaxa() {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) return false;
        if (tipo == null || tipo.trim().isEmpty()) return false;
        if (cliente == null || !cliente.validarDados()) return false;
        if (contrato == null) return false;
        return true;
    }

    /**
     * Formata os dados da taxa para exibição em relatórios, UI ou PDFs.
     * Reutilizável, chamando formatação do Cliente.
     * @return String formatada (ex.: "Taxa Inicial de 500.00, Cliente: João Silva")
     */
    public String toStringFormatado() {
        return String.format("Taxa %s de %.2f, Cliente: %s", tipo, valor, cliente.getNomeCompleto());
    }

    @Override
    public String toString() {
        return "TaxaInstalacao{id=" + id + ", valor=" + valor + ", tipo='" + tipo + "', cliente=" + cliente +
                ", contrato=" + contrato + "}";
    }
}
