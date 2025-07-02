package com.concursoacm.application.dtos.resultados;

/**
 * *DTO que representa la puntuación total de una región.
 */
public class PuntuacionPorRegionDTO {
    private String nombreRegion;
    private int totalPuntos;

    /**
     * *Constructor que inicializa el DTO con los datos de la región y su
     * *puntuación.
     *
     * @param nombreRegion    Nombre de la región.
     * @param puntuacionTotal Puntuación total de la región.
     */
    public PuntuacionPorRegionDTO(String nombreRegion, int totalPuntos) {
        this.nombreRegion = nombreRegion;
        this.totalPuntos = totalPuntos;
    }

    public String getNombreRegion() {
        return nombreRegion;
    }

    public void setNombreRegion(String nombreRegion) {
        this.nombreRegion = nombreRegion;
    }

    public int getTotalPuntos() {
        return totalPuntos;
    }

    public void setTotalPuntos(int totalPuntos) {
        this.totalPuntos = totalPuntos;
    }
}
