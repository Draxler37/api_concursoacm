package com.concursoacm.tools.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concursoacm.models.Respuesta;

import java.util.List;

/**
 * Repositorio para la gestión de la entidad Respuesta.
 * Proporciona métodos para realizar operaciones CRUD y consultas
 * personalizadas.
 */
@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Integer> {

    /**
     * Obtiene las respuestas de un participante.
     *
     * @param idParticipante ID del participante.
     * @return Lista de respuestas del participante.
     */
    List<Respuesta> findByParticipanteIdParticipante(int idParticipante);

    /**
     * Verifica si un participante ya ha respondido a una pregunta específica.
     *
     * @param idParticipante ID del participante.
     * @param idPregunta     ID de la pregunta.
     * @return true si ya existe una respuesta, false en caso contrario.
     */
    boolean existsByParticipanteIdParticipanteAndPreguntaIdPregunta(int idParticipante, int idPregunta);
}
