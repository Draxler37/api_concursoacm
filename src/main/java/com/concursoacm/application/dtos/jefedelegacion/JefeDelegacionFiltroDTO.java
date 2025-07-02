package com.concursoacm.application.dtos.jefedelegacion;

/**
 * DTO extendido para jefe de delegación con país y región (para filtrado
 * frontend).
 */
public class JefeDelegacionFiltroDTO {
    private int idJefe;
    private String nombreParticipante;
    private Integer idPais;
    private String nombrePais;
    private Integer idRegion;
    private String nombreRegion;
    private Integer edad;
    private String sexo;

    public JefeDelegacionFiltroDTO() {
    }

    public JefeDelegacionFiltroDTO(int idJefe, String nombreParticipante, Integer idPais, String nombrePais,
            Integer idRegion, String nombreRegion, Integer edad, String sexo) {
        this.idJefe = idJefe;
        this.nombreParticipante = nombreParticipante;
        this.idPais = idPais;
        this.nombrePais = nombrePais;
        this.idRegion = idRegion;
        this.nombreRegion = nombreRegion;
        this.edad = edad;
        this.sexo = sexo;
    }

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

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public Integer getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    public String getNombreRegion() {
        return nombreRegion;
    }

    public void setNombreRegion(String nombreRegion) {
        this.nombreRegion = nombreRegion;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
