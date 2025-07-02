package com.concursoacm.application.dtos.preguntas;

import java.util.List;

/**
 * *DTO que representa los detalles de las preguntas asignadas a un equipo.
 */
public class PreguntasAsignadasDetalleDTO {

    private String nombreEquipo;
    private List<PreguntaDetalleDTO> preguntas;

    public PreguntasAsignadasDetalleDTO() {
    }

    /**
     * *Constructor que inicializa el DTO con los datos de las preguntas asignadas.
     *
     * @param nombreEquipo Nombre del equipo.
     * @param preguntas    Lista de preguntas asignadas.
     */
    public PreguntasAsignadasDetalleDTO(String nombreEquipo, List<PreguntaDetalleDTO> preguntas) {
        this.nombreEquipo = nombreEquipo;
        this.preguntas = preguntas;
    }

    /**
     * *Obtiene el nombre del equipo.
     *
     * @return Nombre del equipo.
     */
    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    /**
     * *Obtiene la lista de preguntas asignadas.
     *
     * @return Lista de preguntas.
     */
    public List<PreguntaDetalleDTO> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<PreguntaDetalleDTO> preguntas) {
        this.preguntas = preguntas;
    }
}
