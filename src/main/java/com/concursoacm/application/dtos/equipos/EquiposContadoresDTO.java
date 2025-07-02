package com.concursoacm.application.dtos.equipos;

public class EquiposContadoresDTO {
    private int total;
    private int conPreguntas;
    private int sinPreguntas;

    public EquiposContadoresDTO(int total, int conPreguntas, int sinPreguntas) {
        this.total = total;
        this.conPreguntas = conPreguntas;
        this.sinPreguntas = sinPreguntas;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getConPreguntas() {
        return conPreguntas;
    }

    public void setConPreguntas(int conPreguntas) {
        this.conPreguntas = conPreguntas;
    }

    public int getSinPreguntas() {
        return sinPreguntas;
    }

    public void setSinPreguntas(int sinPreguntas) {
        this.sinPreguntas = sinPreguntas;
    }
}
