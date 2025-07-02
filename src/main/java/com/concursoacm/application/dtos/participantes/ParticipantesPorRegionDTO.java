package com.concursoacm.application.dtos.participantes;

import java.util.List;

/**
 * *DTO que representa los participantes agrupados por región.
 */
public class ParticipantesPorRegionDTO {

    private String nombreRegion;
    private List<ParticipanteDTO> participantes;

    /**
     * *Constructor que inicializa el DTO con el nombre de la región y la lista de
     * participantes.
     *
     * @param nombreRegion  Nombre de la región.
     * @param participantes Lista de participantes en la región.
     */
    public ParticipantesPorRegionDTO(String nombreRegion, List<ParticipanteDTO> participantes) {
        this.nombreRegion = nombreRegion;
        this.participantes = participantes;
    }

    /**
     * *Obtiene el nombre de la región.
     *
     * @return Nombre de la región.
     */
    public String getNombreRegion() {
        return nombreRegion;
    }

    /**
     * *Establece el nombre de la región.
     *
     * @param nombreRegion Nombre de la región.
     */
    public void setNombreRegion(String nombreRegion) {
        this.nombreRegion = nombreRegion;
    }

    /**
     * *Obtiene la lista de participantes en la región.
     *
     * @return Lista de participantes.
     */
    public List<ParticipanteDTO> getParticipantes() {
        return participantes;
    }

    /**
     * *Establece la lista de participantes en la región.
     *
     * @param participantes Lista de participantes.
     */
    public void setParticipantes(List<ParticipanteDTO> participantes) {
        this.participantes = participantes;
    }
}
