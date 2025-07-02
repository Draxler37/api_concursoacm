package com.concursoacm.application.dtos.resultados;

/**
 * *DTO que representa la puntuación total de un país.
 */
public class PuntuacionPorPaisDTO {
    private String nombrePais;
    private int totalPuntos;

    /**
     * *Constructor que inicializa el DTO con los datos del país y su puntuación.
     *
     * @param nombrePais  Nombre del país.
     * @param totalPuntos Puntuación total del país.
     */
    public PuntuacionPorPaisDTO(String nombrePais, int totalPuntos) {
        this.nombrePais = nombrePais;
        this.totalPuntos = totalPuntos;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public int getTotalPuntos() {
        return totalPuntos;
    }

    public void setTotalPuntos(int totalPuntos) {
        this.totalPuntos = totalPuntos;
    }
}
