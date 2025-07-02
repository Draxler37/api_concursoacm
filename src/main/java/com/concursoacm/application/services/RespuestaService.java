package com.concursoacm.application.services;

import com.concursoacm.interfaces.services.IRespuestaService;
import com.concursoacm.models.Participante;
import com.concursoacm.models.Pregunta;
import com.concursoacm.models.Respuesta;
import com.concursoacm.application.dtos.respuestas.RespuestaDTO;
import com.concursoacm.tools.repositories.ParticipanteRepository;
import com.concursoacm.tools.repositories.EquipoPreguntaRepository;
import com.concursoacm.tools.repositories.RespuestaRepository;
import com.concursoacm.tools.repositories.PreguntaRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * *Servicio que implementa la lógica de negocio para la gestión de respuestas.
 */
@Service
public class RespuestaService implements IRespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final ParticipanteRepository participanteRepository;
    private final EquipoPreguntaRepository equipoPreguntaRepository;
    private final PreguntaRepository preguntaRepository;

    /**
     * *Constructor que inyecta las dependencias necesarias.
     *
     * @param respuestaRepository      Repositorio para la gestión de
     *                                 respuestas.
     * @param participanteRepository   Repositorio para la gestión de
     *                                 participantes.
     * @param equipoPreguntaRepository Repositorio para la gestión de preguntas
     *                                 asignadas.
     * @param preguntaRepository       Repositorio para la gestión de preguntas.
     */
    public RespuestaService(RespuestaRepository respuestaRepository,
            ParticipanteRepository participanteRepository,
            EquipoPreguntaRepository equipoPreguntaRepository,
            PreguntaRepository preguntaRepository) {
        this.respuestaRepository = respuestaRepository;
        this.participanteRepository = participanteRepository;
        this.equipoPreguntaRepository = equipoPreguntaRepository;
        this.preguntaRepository = preguntaRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RespuestaDTO crearRespuesta(int idParticipante, int idPregunta, String respuestaText) {
        Participante participante = validarParticipante(idParticipante);
        Pregunta pregunta = validarPreguntaAsignada(participante, idPregunta);

        Respuesta respuesta = new Respuesta();
        respuesta.setParticipante(participante);
        respuesta.setPregunta(pregunta);
        respuesta.setRespuestaParticipante(respuestaText);
        respuesta.setPuntuacionObtenida(0); // Inicialmente 0

        return new RespuestaDTO(respuestaRepository.save(respuesta));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RespuestaDTO> getRespuestasDelParticipante(int idParticipante) {
        Participante participante = validarParticipante(idParticipante);
        List<Respuesta> respuestas = respuestaRepository
                .findByParticipanteIdParticipante(participante.getIdParticipante());

        return respuestas.stream()
                .map(RespuestaDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * *Valida si un participante existe y tiene un equipo asignado.
     *
     * @param idParticipante ID del participante.
     * @return Objeto Participante validado.
     */
    private Participante validarParticipante(int idParticipante) {
        Participante participante = participanteRepository.findById(idParticipante)
                .orElseThrow(() -> new IllegalArgumentException("Participante no encontrado."));

        if (participante.getEquipo() == null) {
            throw new IllegalArgumentException("El participante no tiene equipo asignado, no puede responder.");
        }

        return participante;
    }

    /**
     * *Valida si una pregunta está asignada al equipo del participante.
     *
     * @param participante Objeto Participante.
     * @param idPregunta   ID de la pregunta.
     * @return Objeto Pregunta validado.
     */
    private Pregunta validarPreguntaAsignada(Participante participante, int idPregunta) {
        int idEquipo = participante.getEquipo().getIdEquipo();

        // Buscar si la pregunta está asignada al equipo
        boolean preguntaAsignada = equipoPreguntaRepository.findByEquipoIdEquipo(idEquipo).stream()
                .anyMatch(asignacion -> asignacion.getPregunta().getIdPregunta() == idPregunta);

        if (!preguntaAsignada) {
            throw new IllegalArgumentException("La pregunta no está asignada a este equipo.");
        }

        // Validar si el participante ya respondió la pregunta
        if (respuestaRepository.existsByParticipanteIdParticipanteAndPreguntaIdPregunta(
                participante.getIdParticipante(), idPregunta)) {
            throw new IllegalArgumentException("El participante ya ha respondido esta pregunta.");
        }

        return preguntaRepository.findById(idPregunta)
                .orElseThrow(() -> new IllegalArgumentException("Pregunta no encontrada."));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean calificarRespuesta(int idRespuesta, int puntuacion) {
        Respuesta respuesta = respuestaRepository.findById(idRespuesta)
                .orElseThrow(() -> new IllegalArgumentException("No se encuentra la respuesta con ID " + idRespuesta));
        
                // Validar que la nota esté en el rango permitido
        if (puntuacion < 0 || puntuacion > 100) {
            throw new IllegalArgumentException("La nota debe estar entre 0 y 100.");
        }

        respuesta.setPuntuacionObtenida(puntuacion);
        respuestaRepository.save(respuesta);

        return true;
    }
}