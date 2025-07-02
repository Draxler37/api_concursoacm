package com.concursoacm.application.dtos.equipos;

import com.concursoacm.models.Equipo;

public class EquipoConPreguntasDTO {
    private int idEquipo;
    private String nombreEquipo;
    private String nombrePais;
    private String nombreCategoria;
    private int cantidadPreguntasAsignadas;

    public EquipoConPreguntasDTO(Equipo equipo, int cantidadPreguntasAsignadas) {
        this.idEquipo = equipo.getIdEquipo();
        this.nombreEquipo = equipo.getNombreEquipo();
        this.nombrePais = equipo.getPais() != null ? equipo.getPais().getNombrePais() : null;
        this.nombreCategoria = equipo.getEquipoCategoria() != null ? equipo.getEquipoCategoria().getNombreCategoria()
                : null;
        this.cantidadPreguntasAsignadas = cantidadPreguntasAsignadas;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public int getCantidadPreguntasAsignadas() {
        return cantidadPreguntasAsignadas;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public void setCantidadPreguntasAsignadas(int cantidadPreguntasAsignadas) {
        this.cantidadPreguntasAsignadas = cantidadPreguntasAsignadas;
    }
}
