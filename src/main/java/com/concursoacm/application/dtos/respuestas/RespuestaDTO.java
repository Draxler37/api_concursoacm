package com.concursoacm.application.dtos.respuestas;

import com.concursoacm.models.Respuesta;

public class RespuestaDTO {

    private int idRespuesta;
    private int idParticipante;
    private int idPregunta;
    private String respuestaParticipante;

    public RespuestaDTO() {
    }

    public RespuestaDTO(Respuesta respuesta) {
        this.idRespuesta = respuesta.getIdRespuesta();
        this.idParticipante = respuesta.getParticipante().getIdParticipante();
        this.idPregunta = respuesta.getPregunta().getIdPregunta();
        this.respuestaParticipante = respuesta.getRespuestaParticipante();
    }

    // Getters y setters
    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getRespuestaParticipante() {
        return respuestaParticipante;
    }

    public void setRespuestaParticipante(String respuestaParticipante) {
        this.respuestaParticipante = respuestaParticipante;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }
}
