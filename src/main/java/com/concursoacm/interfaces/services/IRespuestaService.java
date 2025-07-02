package com.concursoacm.interfaces.services;

import java.util.List;

import com.concursoacm.application.dtos.respuestas.RespuestaDTO;

/**
 * *Interfaz que define los métodos para la gestión de respuestas.
 */
public interface IRespuestaService {

    /**
     * *Crea una nueva respuesta para una pregunta asignada a un participante.
     *
     * @param idParticipante ID del participante.
     * @param idPregunta     ID de la pregunta.
     * @param respuestaText  Texto de la respuesta del participante.
     * @return Objeto Respuesta creado.
     */
    RespuestaDTO crearRespuesta(int idParticipante, int idPregunta, String respuestaText);

    /**
     * *Obtiene las respuestas de un participante.
     *
     * @param idParticipante ID del participante.
     * @return Lista de respuestas del participante.
     */
    List<RespuestaDTO> getRespuestasDelParticipante(int idParticipante);

    /**
     * *Actualiza la puntuación de una respuesta.
     * 
     * @param idRespuesta ID de la respuesta.
     * @param puntuacion  Nueva puntuación (0-100).
     * @return true si se actualizó correctamente.
     */
    boolean calificarRespuesta(int idRespuesta, int puntuacion);
}
