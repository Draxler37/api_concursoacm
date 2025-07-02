package com.concursoacm.application.dtos.resultados;

/**
 * *DTO que representa los ganadores por categoría.
 */
public class PuntuacionPorCategoriaDTO {
    private PuntuacionPorEquipoDTO ganadorCompetencia;
    private PuntuacionPorEquipoDTO ganadorJunior;

    public PuntuacionPorCategoriaDTO() {
    }

    /**
     * *Constructor que inicializa el DTO con los ganadores de cada categoría.
     *
     * @param ganadorCompetencia Ganador de la categoría Competencia.
     * @param ganadorJunior      Ganador de la categoría Junior.
     */
    public PuntuacionPorCategoriaDTO(PuntuacionPorEquipoDTO ganadorCompetencia, PuntuacionPorEquipoDTO ganadorJunior) {
        this.ganadorCompetencia = ganadorCompetencia;
        this.ganadorJunior = ganadorJunior;
    }

    public PuntuacionPorEquipoDTO getGanadorCompetencia() {
        return ganadorCompetencia;
    }

    public void setGanadorCompetencia(PuntuacionPorEquipoDTO ganadorCompetencia) {
        this.ganadorCompetencia = ganadorCompetencia;
    }

    public PuntuacionPorEquipoDTO getGanadorJunior() {
        return ganadorJunior;
    }

    public void setGanadorJunior(PuntuacionPorEquipoDTO ganadorJunior) {
        this.ganadorJunior = ganadorJunior;
    }
}
