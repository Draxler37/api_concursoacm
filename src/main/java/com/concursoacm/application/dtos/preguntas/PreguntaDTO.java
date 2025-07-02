package com.concursoacm.application.dtos.preguntas;

/**
 * *DTO que representa una pregunta.
 */
public class PreguntaDTO {

    private int idPregunta;
    private String texto;
    private int totalPuntos;

    /**
     * *Constructor que inicializa el DTO con los datos de la pregunta.
     *
     * @param idPregunta  ID de la pregunta.
     * @param texto       Texto de la pregunta.
     * @param totalPuntos Puntuación máxima de la pregunta.
     */
    public PreguntaDTO(int idPregunta, String texto, int totalPuntos) {
        this.idPregunta = idPregunta;
        this.texto = texto;
        this.totalPuntos = totalPuntos;
    }

    /**
     * *Obtiene el ID de la pregunta.
     *
     * @return ID de la pregunta.
     */
    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    /**
     * *Obtiene el texto de la pregunta.
     *
     * @return Texto de la pregunta.
     */
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * *Obtiene la puntuación máxima de la pregunta.
     *
     * @return Puntuación máxima.
     */
    public int getTotalPuntos() {
        return totalPuntos;
    }

    public void setTotalPuntos(int totalPuntos) {
        this.totalPuntos = totalPuntos;
    }
}