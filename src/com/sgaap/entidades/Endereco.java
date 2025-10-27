package com.sgaap.entidades;

import java.io.Serializable;

/**
 * Entidade que representa o endereço residencial do cliente no sistema SGAAP.
 * Contém bairro, quarteirão, número da casa e está associada a um Cliente via composição.
 * Integra com Localizacao para geolocalização e Instalacao para canalização.
 * Responsável apenas por armazenar e validar dados de endereço, sem lógica de negócio externa.
 */
public class Endereco implements Serializable {
    private Long id;          // Identificador único para persistência
    private String bairro;    // Nome do bairro (ex.: "Centro")
    private String quarteirao;// Identificador do quarteirão (ex.: "Q5")
    private String numeroCasa;// Número da casa (ex.: "456")
    private Cliente cliente;  // Cliente associado ao endereço

    // Construtor padrão (necessário para frameworks como JPA/Hibernate)
    public Endereco() {
    }

    // Construtor completo para criação explícita
    public Endereco(Long id, String bairro, String quarteirao, String numeroCasa, Cliente cliente) {
        this.id = id;
        this.bairro = bairro;
        this.quarteirao = quarteirao;
        this.numeroCasa = numeroCasa;
        this.cliente = cliente;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getQuarteirao() {
        return quarteirao;
    }

    public void setQuarteirao(String quarteirao) {
        this.quarteirao = quarteirao;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Valida os campos do endereço: verifica se bairro, quarteirão, número da casa e cliente não são nulos ou vazios.
     * Método curto e isolado para fácil teste e manutenção.
     * @return true se todos os campos forem válidos, false caso contrário
     */
    public boolean validarEndereco() {
        if (bairro == null || bairro.trim().isEmpty()) return false;
        if (quarteirao == null || quarteirao.trim().isEmpty()) return false;
        if (numeroCasa == null || numeroCasa.trim().isEmpty()) return false;
        if (cliente == null || !cliente.validarDados()) return false;
        return true;
    }

    /**
     * Formata o endereço como string para exibição em relatórios, UI ou PDFs.
     * Reutilizável, chamando formatação do Cliente.
     * @return String formatada (ex.: "Bairro Centro, Quarteirão Q5, Casa 456, Cliente: João Silva")
     */
    public String toStringFormatado() {
        return String.format("Bairro %s, Quarteirão %s, Casa %s, Cliente: %s",
                bairro, quarteirao, numeroCasa, cliente.getNomeCompleto());
    }

    @Override
    public String toString() {
        return "Endereco{id=" + id + ", bairro='" + bairro + "', quarteirao='" + quarteirao +
                "', numeroCasa='" + numeroCasa + "', cliente=" + cliente + "}";
    }
}