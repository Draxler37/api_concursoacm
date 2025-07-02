package com.concursoacm.application.dtos.participantes;

/**
 * *DTO que representa un participante.
 */
public class ParticipanteDTO {

    private int idParticipante;
    private String nombre;
    private String numCarnet;
    private int edad;
    private String sexo;
    private String nombrePais;
    private String nombreEquipo;

    /**
     * *Constructor que inicializa el DTO con los datos del participante.
     *
     * @param idParticipante ID del participante.
     * @param nombre         Nombre del participante.
     * @param numCarnet      Número de carnet del participante.
     * @param edad           Edad del participante.
     * @param sexo           Sexo del participante.
     * @param nombrePais     Nombre del país del participante.
     * @param nombreEquipo   Nombre del equipo del participante (puede ser "Sin
     *                       equipo").
     */
    public ParticipanteDTO(int idParticipante, String nombre, String numCarnet, int edad, String sexo,
            String nombrePais, String nombreEquipo) {
        this.idParticipante = idParticipante;
        this.nombre = nombre;
        this.numCarnet = numCarnet;
        this.edad = edad;
        this.sexo = sexo;
        this.nombrePais = nombrePais;
        this.nombreEquipo = nombreEquipo;
    }

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
     * *Obtiene el nombre del país del participante.
     *
     * @return Nombre del país.
     */
    public String getNombrePais() {
        return nombrePais;
    }

    /**
     * *Establece el nombre del país del participante.
     *
     * @param nombrePais Nombre del país.
     */
    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    /**
     * *Obtiene el nombre del equipo del participante.
     *
     * @return Nombre del equipo.
     */
    public String getNombreEquipo() {
        return nombreEquipo;
    }

    /**
     * *Establece el nombre del equipo del participante.
     *
     * @param nombreEquipo Nombre del equipo.
     */
    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }
}
