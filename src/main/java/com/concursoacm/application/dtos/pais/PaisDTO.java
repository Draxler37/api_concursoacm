package com.concursoacm.application.dtos.pais;

import com.concursoacm.models.Pais;

/**
 * *Data Transfer Object (DTO) para la entidad Pais.
 */
public class PaisDTO {

    private int idPais;
    private String nombrePais;
    private String codigoTelefonico;
    private String nombreRegion;

    // Constructor sin argumentos (requerido por Jackson)
    public PaisDTO() {
    }

    /**
     * *Constructor que inicializa el DTO a partir de un objeto Pais.
     *
     * @param pais Objeto Pais.
     */
    public PaisDTO(Pais pais) {
        this.idPais = pais.getIdPais();
        this.nombrePais = pais.getNombrePais();
        this.codigoTelefonico = pais.getCodigoTelefonico();
        this.nombreRegion = pais.getRegion() != null ? pais.getRegion().getNombreRegion() : null;
    }

    // Getters y setters
    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public String getCodigoTelefonico() {
        return codigoTelefonico;
    }

    public void setCodigoTelefonico(String codigoTelefonico) {
        this.codigoTelefonico = codigoTelefonico;
    }

    public String getNombreRegion() {
        return nombreRegion;
    }

    public void setNombreRegion(String nombreRegion) {
        this.nombreRegion = nombreRegion;
    }
}