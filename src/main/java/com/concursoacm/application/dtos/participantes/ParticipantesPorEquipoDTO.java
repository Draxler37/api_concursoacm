package com.concursoacm.application.dtos.participantes;

import java.util.List;

public class ParticipantesPorEquipoDTO {
    private String nombreEquipo;
    private List<ParticipanteDTO> participantes;

    public ParticipantesPorEquipoDTO(String nombreEquipo, List<ParticipanteDTO> participantes) {
        this.nombreEquipo = nombreEquipo;
        this.participantes = participantes;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public List<ParticipanteDTO> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<ParticipanteDTO> participantes) {
        this.participantes = participantes;
    }
}
