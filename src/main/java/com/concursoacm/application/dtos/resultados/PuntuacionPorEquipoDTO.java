package com.concursoacm.application.dtos.resultados;

/**
 * *DTO que representa un equipo con su puntuación total.
 */
public class PuntuacionPorEquipoDTO {

    private String nombreEquipo;
    private int totalPuntos;

    /**
     * *Constructor que inicializa el DTO con el nombre del equipo y su puntuación
     * total.
     *
     * @param nombreEquipo Nombre del equipo.
     * @param totalPuntos  Puntuación total del equipo.
     */
    public PuntuacionPorEquipoDTO(String nombreEquipo, int totalPuntos) {
        this.nombreEquipo = nombreEquipo;
        this.totalPuntos = totalPuntos;
    }

    /**
     * *Obtiene el nombre del equipo.
     *
     * @return Nombre del equipo.
     */
    public String getNombreEquipo() {
        return nombreEquipo;
    }

    /**
     * *Establece el nombre del equipo.
     *
     * @param nombreEquipo Nombre del equipo.
     */
    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    /**
     * *Obtiene la puntuación total del equipo.
     *
     * @return Puntuación total.
     */
    public int getTotalPuntos() {
        return totalPuntos;
    }

    /**
     * *Establece la puntuación total del equipo.
     *
     * @param totalPuntos Puntuación total.
     */
    public void setTotalPuntos(int totalPuntos) {
        this.totalPuntos = totalPuntos;
    }
}
