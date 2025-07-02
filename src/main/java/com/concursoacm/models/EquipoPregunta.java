package com.concursoacm.models;

import jakarta.persistence.*;

/**
 * *Clase que representa la relación entre un equipo y una pregunta en el
 * *sistema.
 * *Esta entidad se utiliza para asignar preguntas a equipos específicos.
 */
@Entity
@Table(name = "equipos_preguntas")
public class EquipoPregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEquipoPregunta;

    @ManyToOne
    @JoinColumn(name = "id_equipo", nullable = false)
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "id_pregunta", nullable = false)
    private Pregunta pregunta;

    // Getters y setters
    /**
     * *Obtiene el ID de la relación entre el equipo y la pregunta.
     * 
     * @return ID de la relación.
     */
    public int getIdEquipoPregunta() {
        return idEquipoPregunta;
    }

    public void setIdEquipoPregunta(int idEquipoPregunta) {
        this.idEquipoPregunta = idEquipoPregunta;
    }

    /**
     * *Obtiene el equipo asociado a la pregunta.
     * 
     * @return Pregunta asociada.
     */
    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    /**
     * *Obtiene la pregunta asociada al equipo.
     * 
     * @return Pregunta asociada.
     */
    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }
}