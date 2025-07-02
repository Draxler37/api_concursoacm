package com.concursoacm.application.services;

import com.concursoacm.interfaces.services.IPreguntaService;
import com.concursoacm.models.Pregunta;
import com.concursoacm.models.PreguntaClase;
import com.concursoacm.tools.repositories.PreguntaRepository;
import com.concursoacm.tools.repositories.PreguntaClaseRepository;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * *Servicio que implementa la lógica de negocio para la gestión de preguntas.
 */
@Service
public class PreguntaService implements IPreguntaService {

    private final PreguntaRepository preguntaRepository;
    private final PreguntaClaseRepository preguntaClaseRepository;

    public PreguntaService(PreguntaRepository preguntaRepository, PreguntaClaseRepository preguntaClaseRepository) {
        this.preguntaRepository = preguntaRepository;
        this.preguntaClaseRepository = preguntaClaseRepository;
    }

    /**
     * *Obtiene todas las preguntas.
     *
     * @return Lista de preguntas.
     */
    @Override
    public List<Pregunta> obtenerTodasLasPreguntas() {
        return preguntaRepository.findAll();
    }

    /**
     * *Obtiene una pregunta por su ID.
     *
     * @param idPregunta ID de la pregunta.
     * @return Pregunta encontrada.
     */
    @Override
    public Pregunta obtenerPreguntaPorId(int idPregunta) {
        return preguntaRepository.findById(idPregunta)
                .orElseThrow(() -> new IllegalArgumentException("La pregunta con ID " + idPregunta + " no existe."));
    }

    /**
     * *Crea una nueva pregunta.
     *
     * @param pregunta Pregunta a crear.
     * @return Pregunta creada.
     */
    @Override
    public Pregunta crearPregunta(Pregunta pregunta) {
        pregunta.setClase(preguntaClaseRepository.findById(pregunta.getClase().getIdClase())
                .orElseThrow(() -> new IllegalArgumentException(
                        "La clase con ID " + pregunta.getClase().getIdClase() + " no existe.")));

        pregunta.setUsada(false); // Inicializa la pregunta como no usada
        return preguntaRepository.save(pregunta);
    }

    /**
     * *Actualiza una pregunta existente.
     *
     * @param idPregunta ID de la pregunta a actualizar.
     * @param pregunta   Pregunta con los nuevos datos.
     * @return Pregunta actualizada.
     */
    @Override
    public Pregunta actualizarPregunta(int idPregunta, Pregunta pregunta) {
        pregunta.setClase(preguntaClaseRepository.findById(pregunta.getClase().getIdClase())
                .orElseThrow(() -> new IllegalArgumentException(
                        "La clase con ID " + pregunta.getClase().getIdClase() + " no existe.")));
        Pregunta existente = obtenerPreguntaPorId(idPregunta);
        existente.setTexto(pregunta.getTexto());
        existente.setPuntuacionMaxima(pregunta.getPuntuacionMaxima());
        existente.setClase(pregunta.getClase());
        existente.setUsada(pregunta.isUsada());
        return preguntaRepository.save(existente);
    }

    /**
     * *Elimina una pregunta por su ID.
     *
     * @param idPregunta ID de la pregunta a eliminar.
     * @return true si la pregunta fue eliminada, false en caso contrario.
     */
    @Override
    public boolean eliminarPregunta(int idPregunta) {
        if (preguntaRepository.existsById(idPregunta)) {
            preguntaRepository.deleteById(idPregunta);
            return true;
        }
        throw new IllegalArgumentException("La pregunta con ID " + idPregunta + " no existe.");
    }

    /**
     * Obtiene todas las clases de preguntas.
     * 
     * @return Lista de clases de preguntas.
     */
    public List<PreguntaClase> obtenerClasesDePreguntas() {
        return preguntaClaseRepository.findAll();
    }
}
