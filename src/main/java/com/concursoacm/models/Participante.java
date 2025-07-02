package com.concursoacm.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * *Entidad que representa un participante en el sistema.
 */
@Entity
@Table(name = "participantes")
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idParticipante;

    @Column(nullable = false)
    @NotBlank(message = "El nombre no puede estar vacío.")
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message = "El número de carnet no puede estar vacío.")
    private String numCarnet;

    @Column(nullable = false)
    @Positive(message = "La edad debe ser un número positivo mayor a 0.")
    private int edad;

    @Column(nullable = false)
    @NotBlank(message = "El sexo no puede estar vacío.")
    private String sexo;

    @ManyToOne
    @JoinColumn(name = "id_pais", nullable = false)
    @JsonBackReference(value = "pais-participantes")
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "id_equipo")
    @JsonBackReference(value = "equipo-participantes")
    private Equipo equipo;

    /**
     * *Obtiene el ID del participante.
     *
     * @return ID del participante.
     */
    public int getIdParticipante() {
        return idParticipante;
    }

    /**
     * *Establece el ID del participante.
     *
     * @param idParticipante ID del participante.
     */
    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }

    /**
     * *Obtiene el nombre del participante.
     *
     * @return Nombre del participante.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * *Establece el nombre del participante.
     *
     * @param nombre Nombre del participante.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * *Obtiene el número de carnet del participante.
     *
     * @return Número de carnet.
     */
    public String getNumCarnet() {
        return numCarnet;
    }

    /**
     * *Establece el número de carnet del participante.
     *
     * @param numCarnet Número de carnet.
     */
    public void setNumCarnet(String numCarnet) {
        this.numCarnet = numCarnet;
    }

    /**
     * *Obtiene la edad del participante.
     *
     * @return Edad del participante.
     */
    public int getEdad() {
        return edad;
    }

    /**
     * *Establece la edad del participante.
     *
     * @param edad Edad del participante.
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * *Obtiene el sexo del participante.
     *
     * @return Sexo del participante.
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * *Establece el sexo del participante.
     *
     * @param sexo Sexo del participante.
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * *Obtiene el país asociado al participante.
     *
     * @return Objeto Pais asociado.
     */
    public Pais getPais() {
        return pais;
    }

    /**
     * *Establece el país asociado al participante.
     *
     * @param pais Objeto Pais asociado.
     */
    public void setPais(Pais pais) {
        this.pais = pais;
    }

    /**
     * *Obtiene el equipo asociado al participante.
     *
     * @return Objeto Equipo asociado o null si no tiene equipo.
     */
    public Equipo getEquipo() {
        return equipo;
    }

    /**
     * *Establece el equipo asociado al participante.
     *
     * @param equipo Objeto Equipo asociado.
     */
    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}
