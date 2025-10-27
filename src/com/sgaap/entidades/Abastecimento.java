package com.sgaap.entidades;

import java.io.Serializable;

/**
 * Entidade que representa a infraestrutura geral de abastecimento de água no sistema SGAAP.
 * Armazena dados da fonte de água, detalhes da canalização (PVC enterrada) e consumo agregado.
 * Responsável apenas por armazenar e validar dados da infraestrutura, sem lógica de negócio externa.
 * Associada a Instalacao para conectar a infraestrutura às residências dos clientes.
 */
public class Abastecimento implements Serializable {
    private Long id;                    // Identificador único para persistência
    private String fonteAgua;           // Origem da água (ex.: "Reservatório Central")
    private String detalhesInfra;       // Detalhes técnicos (ex.: "Canalização PVC enterrada, diâmetro 100mm")
    private Double consumoAgregado;     // Consumo total em m³ (somatório de instalações)

    // Construtor padrão (necessário para frameworks como JPA/Hibernate)
    public Abastecimento() {
    }

    // Construtor completo para criação explícita
    public Abastecimento(Long id, String fonteAgua, String detalhesInfra, Double consumoAgregado) {
        this.id = id;
        this.fonteAgua = fonteAgua;
        this.detalhesInfra = detalhesInfra;
        this.consumoAgregado = consumoAgregado;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFonteAgua() {
        return fonteAgua;
    }

    public void setFonteAgua(String fonteAgua) {
        this.fonteAgua = fonteAgua;
    }

    public String getDetalhesInfra() {
        return detalhesInfra;
    }

    public void setDetalhesInfra(String detalhesInfra) {
        this.detalhesInfra = detalhesInfra;
    }

    public Double getConsumoAgregado() {
        return consumoAgregado;
    }

    public void setConsumoAgregado(Double consumoAgregado) {
        this.consumoAgregado = consumoAgregado;
    }

    /**
     * Valida os dados do abastecimento: fonte e detalhes da infraestrutura não podem ser nulos/vazios,
     * consumo agregado deve ser não-negativo.
     * Método curto para fácil teste e manutenção.
     * @return true se válido, false caso contrário
     */
    public boolean validarAbastecimento() {
        if (fonteAgua == null || fonteAgua.trim().isEmpty()) return false;
        if (detalhesInfra == null || detalhesInfra.trim().isEmpty()) return false;
        if (consumoAgregado == null || consumoAgregado < 0.0) return false;
        return true;
    }

    /**
     * Formata os dados do abastecimento para exibição em relatórios, UI ou PDFs.
     * Reutilizável, sem dependências externas.
     * @return String formatada (ex.: "Fonte: Reservatório Central, Consumo: 1500.0 m³")
     */
    public String toStringFormatado() {
        return String.format("Fonte: %s, Consumo: %.2f m³", fonteAgua, consumoAgregado);
    }

    @Override
    public String toString() {
        return "Abastecimento{id=" + id + ", fonteAgua='" + fonteAgua + "', detalhesInfra='" + detalhesInfra +
                "', consumoAgregado=" + consumoAgregado + "}";
    }
}