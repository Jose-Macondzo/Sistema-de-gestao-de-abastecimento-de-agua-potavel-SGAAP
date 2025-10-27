package com.sgaap.entidades;

import java.io.Serializable;

/**
 * Entidade que representa as coordenadas geográficas (latitude e longitude) da residência do cliente.
 * Responsável apenas por armazenar e validar dados de localização, sem lógica de negócio externa (ex.: mapas).
 * Associada a Endereco para correlacionar com endereço físico; composta por Cliente.
 */
public class Localizacao implements Serializable {
    private Long id;         // Identificador único para persistência
    private Double latitude; // Latitude da residência (ex.: -25.8918)
    private Double longitude;// Longitude da residência (ex.: 32.6052)

    // Construtor padrão (necessário para frameworks como JPA/Hibernate)
    public Localizacao() {
    }

    // Construtor completo para criação explícita
    public Localizacao(Long id, Double latitude, Double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Valida as coordenadas: latitude entre -90 e 90, longitude entre -180 e 180.
     * Método curto e isolado para fácil teste e manutenção.
     * @return true se válido, false caso contrário
     */
    public boolean validarCoordenadas() {
        if (latitude == null || latitude < -90.0 || latitude > 90.0) return false;
        if (longitude == null || longitude < -180.0 || longitude > 180.0) return false;
        return true;
    }

    /**
     * Formata as coordenadas para exibição em relatórios, UI ou integração com mapas.
     * Simples e reutilizável, sem dependências externas.
     * @return String formatada (ex.: "Lat: -25.8918, Long: 32.6052")
     */
    public String toStringFormatado() {
        return String.format("Lat: %.6f, Long: %.6f", latitude, longitude);
    }

    /**
     * Calcula a distância aproximada para outra localização (em km, usando fórmula de Haversine simplificada).
     * Usado para verificações internas ou relatórios, mantido simples.
     * @param outra Localização a comparar
     * @return Distância em quilômetros
     */
    public double calcularDistancia(Localizacao outra) {
        if (outra == null || !this.validarCoordenadas() || !outra.validarCoordenadas()) {
            return 0.0;
        }
        double earthRadius = 6371.0; // Raio da Terra em km
        double dLat = Math.toRadians(outra.latitude - this.latitude);
        double dLon = Math.toRadians(outra.longitude - this.longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(outra.latitude)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    @Override
    public String toString() {
        return "Localizacao{id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + "}";
    }
}