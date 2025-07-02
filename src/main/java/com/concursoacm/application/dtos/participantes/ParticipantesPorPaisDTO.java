package com.concursoacm.application.dtos.participantes;

import java.util.List;

/**
 * *DTO que representa los participantes agrupados por país.
 */
public class ParticipantesPorPaisDTO {

    private String nombrePais;
    private List<ParticipanteDTO> participantes;

    /**
     * *Constructor que inicializa el DTO con el nombre del país y la lista de
     * participantes.
     *
     * @param nombrePais    Nombre del país.
     * @param participantes Lista de participantes en el país.
     */
    public ParticipantesPorPaisDTO(String nombrePais, List<ParticipanteDTO> participantes) {
        this.nombrePais = nombrePais;
        this.participantes = participantes;
    }

    /**
     * *Obtiene el nombre del país.
     *
     * @return Nombre del país.
     */
    public String getNombrePais() {
        return nombrePais;
    }

    /**
     * *Establece el nombre del país.
     *
     * @param nombrePais Nombre del país.
     */
    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    /**
     * *Obtiene la lista de participantes en el país.
     *
     * @return Lista de participantes.
     */
    public List<ParticipanteDTO> getParticipantes() {
        return participantes;
    }

    /**
     * *Establece la lista de participantes en el país.
     *
     * @param participantes Lista de participantes.
     */
    public void setParticipantes(List<ParticipanteDTO> participantes) {
        this.participantes = participantes;
    }
}
