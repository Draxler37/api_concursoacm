package com.concursoacm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * *Entidad que representa una respuesta en el sistema.
 */
@Entity
@Table(name = "respuestas")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRespuesta;

    @Column(nullable = false)
    @NotBlank(message = "La respuesta del participante no puede estar vacía.")
    private String respuestaParticipante;

    @ManyToOne
    @JoinColumn(name = "id_participante", nullable = false)
    private Participante participante;

    @ManyToOne
    @JoinColumn(name = "id_pregunta", nullable = false)
    private Pregunta pregunta;

    @Column(nullable = false)
    @PositiveOrZero(message = "La puntuación obtenida no puede ser negativa.")
    private int puntuacionObtenida = 0;

    /**
     * *Obtiene el ID de la respuesta.
     *
     * @return ID de la respuesta.
     */
    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    /**
     * *Obtiene el texto de la respuesta del participante.
     *
     * @return Texto de la respuesta.
     */
    public String getRespuestaParticipante() {
        return respuestaParticipante;
    }

    public void setRespuestaParticipante(String respuestaParticipante) {
        this.respuestaParticipante = respuestaParticipante;
    }

    /**
     * *Obtiene el participante que respondió.
     *
     * @return ID del participante.
     */
    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    /**
     * *Obtiene la pregunta respondida.
     *
     * @return ID de la pregunta.
     */
    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    /**
     * *Obtiene la puntuación obtenida por la respuesta.
     *
     * @return Puntuación obtenida.
     */
    public int getPuntuacionObtenida() {
        return puntuacionObtenida;
    }

    public void setPuntuacionObtenida(int puntuacionObtenida) {
        this.puntuacionObtenida = puntuacionObtenida;
    }
}
