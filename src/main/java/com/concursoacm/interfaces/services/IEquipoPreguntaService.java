package com.concursoacm.interfaces.services;

import com.concursoacm.application.dtos.equipos.EquipoConPreguntasDTO;
import com.concursoacm.application.dtos.equipos.EquiposContadoresDTO;
import com.concursoacm.application.dtos.preguntas.PreguntasAsignadasDetalleDTO;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

public interface IEquipoPreguntaService {

        /**
         * *Asigna preguntas a todos los equipos sin asignación previa.
         */
        void asignarPreguntasATodosLosEquipos();

        /**
         * *Obtiene los detalles de las preguntas asignadas a un equipo.
         *
         * @param idEquipo ID del equipo.
         * @return DTO con los detalles de las preguntas asignadas.
         */
        PreguntasAsignadasDetalleDTO getPreguntasAsignadasAlEquipo(int idEquipo, Authentication authentication)
                        throws IllegalArgumentException, AccessDeniedException;

        /**
         * Obtiene las preguntas asignadas al equipo del participante.
         * Si el participante no tiene equipo, lanza IllegalArgumentException.
         * Si tiene equipo, devuelve las preguntas asignadas a ese equipo.
         */
        PreguntasAsignadasDetalleDTO getPreguntasAsignadasDeParticipante(int idParticipante)
                        throws IllegalArgumentException;

        /**
         * Devuelve la lista de equipos con la cantidad de preguntas asignadas,
         * filtrando por nombre, país, categoría y asignación.
         * 
         * @param nombre      Nombre del equipo (opcional)
         * @param idPais      ID del país (opcional)
         * @param idCategoria ID de la categoría (opcional)
         * @param asignado    null=Todos, true=Con preguntas, false=Sin preguntas
         * @return Lista de equipos con cantidad de preguntas asignadas
         */
        List<EquipoConPreguntasDTO> buscarEquiposConPreguntasAsignadas(String nombre, Integer idPais,
                        Integer idCategoria,
                        Boolean asignado);

        /**
         * Devuelve los contadores de equipos: total, con preguntas y sin preguntas.
         */
        EquiposContadoresDTO obtenerContadoresEquipos();

        /**
         * Asigna preguntas a un equipo específico por su ID.
         * 
         * @param idEquipo ID del equipo
         */
        void asignarPreguntasAEquipoPorId(int idEquipo);
}