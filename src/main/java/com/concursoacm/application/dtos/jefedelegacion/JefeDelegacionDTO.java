package com.concursoacm.application.dtos.jefedelegacion;

/**
 * *DTO que representa un jefe de delegación.
 */
public class JefeDelegacionDTO {
    private int idJefe;
    private String nombreParticipante;

    public JefeDelegacionDTO() {
    }

    /**
     * *Constructor que inicializa el DTO con los datos del jefe de delegación.
     *
     * @param idJefe             ID del jefe de delegación.
     * @param nombreParticipante Nombre del participante asociado al jefe de
     *                           delegación.
     */
    public JefeDelegacionDTO(int idJefe, String nombreParticipante) {
        this.idJefe = idJefe;
        this.nombreParticipante = nombreParticipante;
    }

    // Getters y Setters
    public int getIdJefe() {
        return idJefe;
    }

    public void setIdJefe(int idJefe) {
        this.idJefe = idJefe;
    }

    public String getNombreParticipante() {
        return nombreParticipante;
    }

    public void setNombreParticipante(String nombreParticipante) {
        this.nombreParticipante = nombreParticipante;
    }
}
