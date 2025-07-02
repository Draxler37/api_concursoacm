package com.concursoacm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * *Entidad que representa una pregunta en el sistema.
 */
@Entity
@Table(name = "preguntas")
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPregunta;

    @Column(nullable = false)
    @NotBlank(message = "El texto de la pregunta no puede estar vacío.")
    private String texto;

    @Column(nullable = false)
    @Positive(message = "La puntuación máxima debe ser un número positivo.")
    private int puntuacionMaxima;

    @ManyToOne
    @JoinColumn(name = "ID_Clase", nullable = false)
    private PreguntaClase clase; // Clase A o B

    @Column(nullable = false)
    private boolean usada; // Indica si la pregunta ya fue utilizada

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
    public int getPuntuacionMaxima() {
        return puntuacionMaxima;
    }

    public void setPuntuacionMaxima(int puntuacionMaxima) {
        this.puntuacionMaxima = puntuacionMaxima;
    }

    /**
     * *Obtiene la clase de la pregunta.
     *
     * @return Clase de la pregunta (A o B).
     */
    public PreguntaClase getClase() {
        return clase;
    }

    public void setClase(PreguntaClase clase) {
        this.clase = clase;
    }

    /**
     * *Indica si la pregunta ya fue utilizada.
     *
     * @return true si la pregunta fue utilizada, false en caso contrario.
     */
    public boolean isUsada() {
        return usada;
    }

    public void setUsada(boolean usada) {
        this.usada = usada;
    }
}
