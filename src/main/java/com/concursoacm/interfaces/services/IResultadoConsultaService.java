package com.concursoacm.interfaces.services;

import com.concursoacm.application.dtos.preguntas.PreguntaDTO;
import com.concursoacm.application.dtos.resultados.PuntuacionPorCategoriaDTO;
import com.concursoacm.application.dtos.resultados.PuntuacionPorEquipoDTO;
import com.concursoacm.application.dtos.resultados.PuntuacionPorPaisDTO;
import com.concursoacm.application.dtos.resultados.PuntuacionPorRegionDTO;
import com.concursoacm.application.dtos.resultados.ResultadoDTO;

import java.util.List;

/**
 * *Interfaz que define los métodos para la consulta de resultados.
 */
public interface IResultadoConsultaService {

    /**
     * *Obtiene el resultado calculado para un participante específico.
     *
     * @param idParticipante ID del participante.
     * @return Resultado del participante.
     */
    ResultadoDTO obtenerResultadoPorParticipante(int idParticipante);

    /**
     * *Obtiene las puntuaciones de los equipos por categoría.
     *
     * @param categoria Categoría del equipo (Competencia o Junior).
     * @return Lista de objetos PuntuacionPorEquipoDTO.
     */
    List<PuntuacionPorEquipoDTO> obtenerPuntuacionesPorCategoria(String categoria);

    /**
     * *Obtiene los ganadores de las categorías Competencia y Junior.
     *
     * @return Objeto PuntuacionPorCategoriaDTO con los ganadores de ambas
     *         categorías.
     */
    PuntuacionPorCategoriaDTO obtenerGanadores();

    /**
     * *Obtiene el top 3 de preguntas más puntuadas en la categoría Junior.
     *
     * @return Lista de objetos PreguntaDTO.
     */
    List<PreguntaDTO> obtenerTop3PreguntasJunior();

    /**
     * *Obtiene el país con mayor puntuación en el concurso.
     *
     * @return Objeto PuntuacionPorPaisDTO con el país y su puntuación total.
     */
    PuntuacionPorPaisDTO obtenerPaisMayorPuntuacion();

    /**
     * *Obtiene la región con mayor puntuación en el concurso.
     *
     * @return Objeto PuntuacionPorRegionDTO con la región y su puntuación total.
     */
    PuntuacionPorRegionDTO obtenerRegionMayorPuntuacion();
}