package com.concursoacm.application.dtos.resultados;

public class ResultadoDTO {

    private int idParticipante;
    private int puntuacionTotal;

    public ResultadoDTO() {
    }
    
    public ResultadoDTO(int idParticipante, int puntuacionTotal) {
        this.idParticipante = idParticipante;
        this.puntuacionTotal = puntuacionTotal;
    }

    // Getters y setters
    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }

    public int getPuntuacionTotal() {
        return puntuacionTotal;
    }

    public void setPuntuacionTotal(int puntuacionTotal) {
        this.puntuacionTotal = puntuacionTotal;
    }
}
