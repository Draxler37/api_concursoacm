package com.concursoacm.application.dtos.preguntas;

/**
 * *DTO que representa los detalles de una pregunta.
 */
public class PreguntaDetalleDTO {

    private int idPregunta;
    private String texto;

    public PreguntaDetalleDTO() {
    }

    /**
     * *Constructor que inicializa el DTO con los datos de la pregunta.
     *
     * @param idPregunta ID de la pregunta.
     * @param texto      Texto de la pregunta.
     */
    public PreguntaDetalleDTO(int idPregunta, String texto) {
        this.idPregunta = idPregunta;
        this.texto = texto;
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
}
