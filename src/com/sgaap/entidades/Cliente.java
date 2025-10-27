package com.sgaap.entidades;

import java.io.Serializable;
import java.util.List;

/**
 * Entidade que representa o cliente do sistema SGAAP, contendo dados pessoais como nome, telefones e email.
 * Compõe Endereco e Localizacao para endereço residencial e geolocalização.
 * Responsável apenas por armazenar e validar dados do cliente, sem lógica de negócio externa.
 */
public class Cliente implements Serializable {
    private Long id;                  // Identificador único para persistência
    private String nomeCompleto;      // Nome completo do cliente (ex.: "João Silva")
    private List<String> telefones;   // Lista de telefones (ex.: ["+258123456", "+258987654"])
    private String email;             // Email do cliente (ex.: "joao@exemplo.com")
    private Endereco endereco;        // Composição com Endereco (bairro, quarteirão, nº casa)
    private Localizacao localizacao;  // Composição com Localizacao (latitude, longitude)

    // Construtor padrão (necessário para frameworks como JPA/Hibernate)
    public Cliente() {
    }

    // Construtor completo para criação explícita
    public Cliente(Long id, String nomeCompleto, List<String> telefones, String email, Endereco endereco, Localizacao localizacao) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.telefones = telefones;
        this.email = email;
        this.endereco = endereco;
        this.localizacao = localizacao;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public List<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<String> telefones) {
        this.telefones = telefones;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    /**
     * Valida os dados do cliente: nome completo e endereço não podem ser nulos/vazios,
     * pelo menos um telefone é necessário, email deve ter formato básico.
     * Método curto para fácil teste e manutenção.
     *
     * @return true se válido, false caso contrário
     */
    public boolean validarDados() {
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) return false;
        if (telefones == null || telefones.isEmpty()) return false;
        if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) return false;
        if (endereco == null || !endereco.validarEndereco()) return false;
        if (localizacao == null) return false;
        return true;
    }

    /**
     * Formata os dados do cliente para exibição em relatórios, UI ou PDFs.
     * Reutilizável, chamando formatação do Endereco.
     *
     * @return String formatada (ex.: "João Silva, joao@exemplo.com, Bairro Centro, Quarteirão Q5, Casa 456")
     */
    public String toStringFormatado() {
        return String.format("%s, %s, %s", nomeCompleto, email, endereco.toStringFormatado());
    }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nomeCompleto='" + nomeCompleto + "', telefones=" + telefones +
                ", email='" + email + "', endereco=" + endereco + ", localizacao=" + localizacao + "}";
    }
}