package com.concursoacm.application.dtos.resultados;

/**
 * *DTO para actualizar la puntuacion de una respuesta.
 */
public class CalificacionRequestDTO {

    private int puntuacion;

    public CalificacionRequestDTO() {
    }

    public CalificacionRequestDTO(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
