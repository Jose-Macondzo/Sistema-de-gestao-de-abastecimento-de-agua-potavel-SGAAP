package com.sgaap.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidade que representa a instalação física da conexão de água para o cliente, incluindo canalização PVC enterrada.
 * Armazena data, custo, detalhes do contador e associações com Abastecimento, Endereco e Cliente.
 * Responsável apenas por armazenar e validar dados da instalação, sem lógica de negócio externa.
 */
public class Instalacao implements Serializable {
    private Long id;                    // Identificador único para persistência
    private LocalDate dataInstalacao;   // Data da instalação (ex.: 2025-09-27)
    private BigDecimal custo;           // Custo total da instalação (link com TaxaInstalacao)
    private String detalhesCanalizacao; // Detalhes técnicos (ex.: "Tubos PVC enterrados, diâmetro 50mm")
    private String contadorId;          // Identificador do contador de água (ex.: "CTR123")
    private Abastecimento abastecimento;// Associação com a infraestrutura de abastecimento
    private Endereco endereco;          // Associação com o endereço da residência
    private Cliente cliente;            // Associação com o cliente proprietário

    // Construtor padrão (necessário para frameworks como JPA/Hibernate)
    public Instalacao() {
    }

    // Construtor completo para criação explícita
    public Instalacao(Long id, LocalDate dataInstalacao, BigDecimal custo, String detalhesCanalizacao,
                      String contadorId, Abastecimento abastecimento, Endereco endereco, Cliente cliente) {
        this.id = id;
        this.dataInstalacao = dataInstalacao;
        this.custo = custo;
        this.detalhesCanalizacao = detalhesCanalizacao;
        this.contadorId = contadorId;
        this.abastecimento = abastecimento;
        this.endereco = endereco;
        this.cliente = cliente;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInstalacao() {
        return dataInstalacao;
    }

    public void setDataInstalacao(LocalDate dataInstalacao) {
        this.dataInstalacao = dataInstalacao;
    }

    public BigDecimal getCusto() {
        return custo;
    }

    public void setCusto(BigDecimal custo) {
        this.custo = custo;
    }

    public String getDetalhesCanalizacao() {
        return detalhesCanalizacao;
    }

    public void setDetalhesCanalizacao(String detalhesCanalizacao) {
        this.detalhesCanalizacao = detalhesCanalizacao;
    }

    public String getContadorId() {
        return contadorId;
    }

    public void setContadorId(String contadorId) {
        this.contadorId = contadorId;
    }

    public Abastecimento getAbastecimento() {
        return abastecimento;
    }

    public void setAbastecimento(Abastecimento abastecimento) {
        this.abastecimento = abastecimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Valida os dados da instalação: data, custo, detalhes da canalização, contador, abastecimento, endereço e cliente não podem ser nulos/vazios.
     * Método curto para fácil teste e manutenção.
     * @return true se válido, false caso contrário
     */
    public boolean validarInstalacao() {
        if (dataInstalacao == null) return false;
        if (custo == null || custo.compareTo(BigDecimal.ZERO) <= 0) return false;
        if (detalhesCanalizacao == null || detalhesCanalizacao.trim().isEmpty()) return false;
        if (contadorId == null || contadorId.trim().isEmpty()) return false;
        if (abastecimento == null) return false;
        if (endereco == null || !endereco.validarEndereco()) return false;
        if (cliente == null || !cliente.validarDados()) return false;
        return true;
    }

    /**
     * Formata os dados da instalação para exibição em relatórios, UI ou PDFs.
     * Reutilizável, chamando formatação do Endereco e Cliente.
     * @return String formatada (ex.: "Instalação em 2025-09-27, Bairro Centro, Quarteirão Q5, Casa 456, Cliente: João Silva, Contador CTR123")
     */
    public String toStringFormatado() {
        return String.format("Instalação em %s, %s, Cliente: %s, Contador %s",
                dataInstalacao, endereco.toStringFormatado(), cliente.getNomeCompleto(), contadorId);
    }

    @Override
    public String toString() {
        return "Instalacao{id=" + id + ", dataInstalacao=" + dataInstalacao + ", custo=" + custo +
                ", detalhesCanalizacao='" + detalhesCanalizacao + "', contadorId='" + contadorId +
                "', abastecimento=" + abastecimento + ", endereco=" + endereco + ", cliente=" + cliente + "}";
    }
}