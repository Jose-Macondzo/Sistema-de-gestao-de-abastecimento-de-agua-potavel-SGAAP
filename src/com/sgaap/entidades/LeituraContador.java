package com.sgaap.entidades;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entidade que representa uma leitura do contador de água no sistema SGAAP, contendo data e metros cúbicos consumidos.
 * Associada a uma Instalacao; usada para gerar faturas.
 * Responsável apenas por armazenar e validar dados da leitura, sem lógica de cálculo externa.
 */
public class LeituraContador implements Serializable {
    private Long id;                  // Identificador único para persistência
    private LocalDate dataLeitura;    // Data da leitura (ex.: 2025-09-27)
    private Double metrosCubicos;     // Consumo em m³ (ex.: 15.5)
    private Instalacao instalacao;    // Instalação associada ao contador

    // Construtor padrão (necessário para frameworks como JPA/Hibernate)
    public LeituraContador() {
    }

    // Construtor completo para criação explícita
    public LeituraContador(Long id, LocalDate dataLeitura, Double metrosCubicos, Instalacao instalacao) {
        this.id = id;
        this.dataLeitura = dataLeitura;
        this.metrosCubicos = metrosCubicos;
        this.instalacao = instalacao;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataLeitura() {
        return dataLeitura;
    }

    public void setDataLeitura(LocalDate dataLeitura) {
        this.dataLeitura = dataLeitura;
    }

    public Double getMetrosCubicos() {
        return metrosCubicos;
    }

    public void setMetrosCubicos(Double metrosCubicos) {
        this.metrosCubicos = metrosCubicos;
    }

    public Instalacao getInstalacao() {
        return instalacao;
    }

    public void setInstalacao(Instalacao instalacao) {
        this.instalacao = instalacao;
    }

    /**
     * Valida os dados da leitura: data e instalação não nulos, metros cúbicos não-negativo.
     * Método curto para fácil teste e manutenção.
     * @return true se válido, false caso contrário
     */
    public boolean validarLeitura() {
        if (dataLeitura == null) return false;
        if (metrosCubicos == null || metrosCubicos < 0.0) return false;
        if (instalacao == null || !instalacao.validarInstalacao()) return false;
        return true;
    }

    /**
     * Calcula o consumo em relação à leitura anterior, se fornecida.
     * Método simples, com lógica detalhada em FaturaServico.
     * @param anterior Leitura anterior (pode ser null)
     * @return Diferença em m³, ou metrosCubicos se anterior for null
     */
    public double calcularConsumoAnterior(LeituraContador anterior) {
        if (anterior == null || !anterior.validarLeitura()) {
            return metrosCubicos != null ? metrosCubicos : 0.0;
        }
        return metrosCubicos - anterior.getMetrosCubicos();
    }

    /**
     * Formata os dados da leitura para exibição em relatórios, UI ou PDFs.
     * Reutilizável, chamando formatação da Instalacao.
     * @return String formatada (ex.: "Leitura em 2025-09-27, 15.50 m³, Contador CTR123")
     */
    public String toStringFormatado() {
        return String.format("Leitura em %s, %.2f m³, Contador %s",
                dataLeitura, metrosCubicos, instalacao.getContadorId());
    }

    @Override
    public String toString() {
        return "LeituraContador{id=" + id + ", dataLeitura=" + dataLeitura + ", metrosCubicos=" + metrosCubicos +
                ", instalacao=" + instalacao + "}";
    }
}