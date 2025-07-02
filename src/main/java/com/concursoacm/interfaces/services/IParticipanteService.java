package com.concursoacm.interfaces.services;

import com.concursoacm.application.dtos.participantes.ParticipanteDTO;
import com.concursoacm.application.dtos.participantes.ParticipantesPorEquipoDTO;
import com.concursoacm.application.dtos.participantes.ParticipantesPorPaisDTO;
import com.concursoacm.application.dtos.participantes.ParticipantesPorRegionDTO;
import com.concursoacm.models.Participante;

import java.util.List;

/**
 * *Interfaz que define los métodos para la gestión de participantes.
 */
public interface IParticipanteService {

    /**
     * *Obtiene todos los participantes como DTOs.
     *
     * @return Lista de objetos ParticipanteDTO.
     */
    List<ParticipanteDTO> getAllParticipantes();

    /**
     * *Obtiene los participantes por país y los empaqueta en un DTO.
     *
     * @param idPais ID del país.
     * @return Objeto ParticipantesPorPaisDTO.
     */
    ParticipantesPorPaisDTO getParticipantesPorPaisDTO(int idPais);

    /**
     * *Obtiene los participantes por región y los empaqueta en un DTO.
     *
     * @param idRegion ID de la región.
     * @return Objeto ParticipantesPorRegionDTO.
     */
    ParticipantesPorRegionDTO getParticipantesPorRegionDTO(int idRegion);

    /**
     * *Obtiene los participantes por equipo y los empaqueta en un DTO.
     *
     * @param idEquipo ID del equipo.
     * @return Lista de objetos ParticipanteDTO.
     */
    ParticipantesPorEquipoDTO getParticipantesPorEquipoDTO(int idEquipo);

    /**
     * *Obtiene un participante por su ID como DTO.
     *
     * @param id ID del participante.
     * @return Objeto ParticipanteDTO o null si no se encuentra.
     */
    ParticipanteDTO getParticipanteById(int id);

    /**
     * *Agrega un nuevo participante.
     *
     * @param participante Objeto Participante a agregar.
     * @return Objeto ParticipanteDTO del participante agregado.
     */
    ParticipanteDTO crearParticipante(Participante participante);

    /**
     * *Asigna un participante a un equipo.
     *
     * @param idParticipante ID del participante.
     * @param idEquipo       ID del equipo.
     * @return Objeto Participante actualizado.
     */
    ParticipanteDTO asignarAlEquipo(int idParticipante, int idEquipo, String usuarioNormalizado);

    /**
     * *Quita un participante de un equipo.
     *
     * @param idParticipante ID del participante.
     * @param idEquipo       ID del equipo.
     * @return Objeto Participante actualizado.
     */
    ParticipanteDTO quitarDelEquipo(int idParticipante, int idEquipo, String usuarioNormalizado);

    /**
     * *Actualiza un participante existente.
     *
     * @param id           ID del participante a actualizar.
     * @param participante Objeto Participante con los nuevos datos.
     * @return Objeto ParticipanteDTO actualizado o null si no se encuentra.
     */
    ParticipanteDTO updateParticipante(int id, Participante participante);

    /**
     * *Elimina un participante por su ID.
     *
     * @param id ID del participante a eliminar.
     * @return true si el participante fue eliminado, false en caso contrario.
     */
    boolean deleteParticipante(int id);

    /**
     * Búsqueda flexible de participantes por nombre, país, equipo y/o región.
     * Todos los parámetros son opcionales.
     * 
     * @param nombre   Nombre del participante (opcional)
     * @param idPais   ID del país (opcional)
     * @param idEquipo ID del equipo (opcional)
     * @param idRegion ID de la región (opcional)
     * @return Lista de ParticipanteDTO que cumplen los criterios
     */
    List<ParticipanteDTO> buscarParticipantes(String nombre, Integer idPais, Integer idEquipo, Integer idRegion);
}