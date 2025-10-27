package com.sgaap.entidades;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entidade que representa o contrato de fornecimento de água no sistema SGAAP.
 * Armazena datas de início/fim, status (ativo/encerrado) e associações com Cliente e TaxaInstalacao.
 * Responsável apenas por armazenar e validar dados do contrato, sem lógica de ativação/encerramento externa.
 */
public class Contrato implements Serializable {
    private Long id;                 // Identificador único para persistência
    private LocalDate dataInicio;    // Data de início do contrato (ex.: 2025-09-27)
    private LocalDate dataFim;       // Data de encerramento, se aplicável (null se ativo)
    private StatusContrato status;   // Status do contrato (Ativo ou Encerrado)
    private Cliente cliente;         // Cliente associado ao contrato
    private TaxaInstalacao taxa;     // Taxa de instalação associada

    // Enum para status do contrato
    public enum StatusContrato {
        ATIVO, ENCERRADO
    }

    // Construtor padrão (necessário para frameworks como JPA/Hibernate)
    public Contrato() {
    }

    // Construtor completo para criação explícita
    public Contrato(Long id, LocalDate dataInicio, LocalDate dataFim, StatusContrato status,
                    Cliente cliente, TaxaInstalacao taxa) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.cliente = cliente;
        this.taxa = taxa;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public StatusContrato getStatus() {
        return status;
    }

    public void setStatus(StatusContrato status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TaxaInstalacao getTaxa() {
        return taxa;
    }

    public void setTaxa(TaxaInstalacao taxa) {
        this.taxa = taxa;
    }

    /**
     * Valida os dados do contrato: data de início e cliente não nulos, status válido, taxa válida.
     * Método curto para fácil teste e manutenção.
     * @return true se válido, false caso contrário
     */
    public boolean validarContrato() {
        if (dataInicio == null) return false;
        if (status == null) return false;
        if (cliente == null || !cliente.validarDados()) return false;
        if (taxa == null || !taxa.validarTaxa()) return false;
        if (status == StatusContrato.ENCERRADO && dataFim == null) return false;
        return true;
    }

    /**
     * Verifica se o contrato está ativo.
     * Método simples para consulta rápida.
     * @return true se ativo, false se encerrado
     */
    public boolean isAtivo() {
        return status == StatusContrato.ATIVO;
    }

    /**
     * Formata os dados do contrato para exibição em relatórios, UI ou PDFs.
     * Reutilizável, chamando formatação do Cliente.
     * @return String formatada (ex.: "Contrato #123, Cliente: João Silva, Início: 2025-09-27, Ativo")
     */
    public String toStringFormatado() {
        String statusStr = isAtivo() ? "Ativo" : "Encerrado em " + dataFim;
        return String.format("Contrato #%d, Cliente: %s, Início: %s, %s",
                id, cliente.getNomeCompleto(), dataInicio, statusStr);
    }

    @Override
    public String toString() {
        return "Contrato{id=" + id + ", dataInicio=" + dataInicio + ", dataFim=" + dataFim +
                ", status=" + status + ", cliente=" + cliente + ", taxa=" + taxa + "}";
    }
}