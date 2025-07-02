package com.concursoacm.application.dtos.equipos;

/**
 * *Data Transfer Object (DTO) para la entidad Equipo.
 */
public class EquipoDTO {

    private int idEquipo;
    private String nombreEquipo;
    private String nombreCategoria;
    private String nombrePais;

    /*
     * *Constructor vacío (necesario para JPA).
     */
    public EquipoDTO() {
    }

    /**
     * *Constructor que inicializa el DTO a partir de un objeto Equipo.
     *
     * @param equipo Objeto Equipo.
     */
    public EquipoDTO(int idEquipo, String nombreEquipo, String nombreCategoria, String nombrePais) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.nombreCategoria = nombreCategoria;
        this.nombrePais = nombrePais;
    }

    // Getters y setters
    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }
}
