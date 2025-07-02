package com.concursoacm.models;

import jakarta.persistence.*;

/**
 * *Entidad que representa una clase de preguntas en el sistema.
 */
@Entity
@Table(name = "preguntas_clases")
public class PreguntaClase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idClase;

    @Column(name = "nombre_clase", nullable = false, unique = true)
    private String nombreClase;

    // Getters y setters
    /**
     * *Obtiene el ID de la clase.
     *
     * @return ID de la clase.
     */
    public int getIdClase() {
        return idClase;
    }

    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }

    /**
     * *Obtiene el nombre de la clase.
     *
     * @return Nombre de la clase.
     */
    public String getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }
}