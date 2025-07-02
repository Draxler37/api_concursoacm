package com.concursoacm.controllers;

import com.concursoacm.application.dtos.preguntas.PreguntaDTO;
import com.concursoacm.application.dtos.resultados.PuntuacionPorCategoriaDTO;
import com.concursoacm.application.dtos.resultados.PuntuacionPorEquipoDTO;
import com.concursoacm.application.dtos.resultados.PuntuacionPorPaisDTO;
import com.concursoacm.application.dtos.resultados.PuntuacionPorRegionDTO;
import com.concursoacm.application.dtos.resultados.ResultadoDTO;
import com.concursoacm.interfaces.services.IResultadoConsultaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * *Controlador REST para la gestión de resultados.
 */
@RestController
@RequestMapping("/resultados")
public class ResultadoController {

    private final IResultadoConsultaService resultadoConsultaService;

    /**
     * *Constructor que inyecta los servicios necesarios.
     *
     * @param resultadoConsultaService Servicio para la consulta de resultados.
     * @param IResultadoCalculoService Servicio para el cálculo de resultados.
     */
    public ResultadoController(IResultadoConsultaService resultadoConsultaService) {
        this.resultadoConsultaService = resultadoConsultaService;
    }

    /**
     * *Obtiene el resultado de un participante específico.
     *
     * @param idParticipante ID del participante.
     * @return Resultado del participante.
     */
    @GetMapping("/{idParticipante}")
    public ResponseEntity<ResultadoDTO> obtenerResultadoPorParticipante(@PathVariable int idParticipante) {
        ResultadoDTO resultado = resultadoConsultaService.obtenerResultadoPorParticipante(idParticipante);
        return ResponseEntity.ok(resultado);
    }

    /**
     * *Obtiene las puntuaciones de los equipos en la categoría Competencia.
     *
     * @return Lista de objetos PuntuacionPorEquipoDTO.
     */
    @GetMapping("/competencia")
    public ResponseEntity<List<PuntuacionPorEquipoDTO>> listarResultadosCompetencia() {
        List<PuntuacionPorEquipoDTO> lista = resultadoConsultaService.obtenerPuntuacionesPorCategoria("Competencia");
        return ResponseEntity.ok(lista);
    }

    /**
     * *Obtiene las puntuaciones de los equipos en la categoría Junior.
     *
     * @return Lista de objetos PuntuacionPorEquipoDTO.
     */
    @GetMapping("/junior")
    public ResponseEntity<List<PuntuacionPorEquipoDTO>> listarResultadosJunior() {
        List<PuntuacionPorEquipoDTO> lista = resultadoConsultaService.obtenerPuntuacionesPorCategoria("Junior");
        return ResponseEntity.ok(lista);
    }

    /**
     * *Obtiene los ganadores de las categorías Competencia y Junior.
     *
     * @return Objeto PuntuacionPorCategoriaDTO.
     */
    @GetMapping("/ganadores")
    public ResponseEntity<PuntuacionPorCategoriaDTO> obtenerGanadoresPorCategoria() {
        PuntuacionPorCategoriaDTO ganadores = resultadoConsultaService.obtenerGanadores();
        return ResponseEntity.ok(ganadores);
    }

    /**
     * *Obtiene el top 3 de preguntas más puntuadas en la categoría Junior.
     *
     * @return Lista de objetos PreguntaDTO.
     */
    @GetMapping("/top-preguntas-junior")
    public ResponseEntity<List<PreguntaDTO>> obtenerTop3PreguntasJunior() {
        List<PreguntaDTO> top = resultadoConsultaService.obtenerTop3PreguntasJunior();
        return ResponseEntity.ok(top);
    }

    /**
     * *Obtiene el país con mayor puntuación en el concurso.
     *
     * @return Objeto PuntuacionPorPaisDTO.
     */
    @GetMapping("/pais-mayor-puntuacion")
    public ResponseEntity<PuntuacionPorPaisDTO> obtenerPaisMayorPuntuacion() {
        PuntuacionPorPaisDTO dto = resultadoConsultaService.obtenerPaisMayorPuntuacion();
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    /**
     * *Obtiene la región con mayor puntuación en el concurso.
     *
     * @return Objeto PuntuacioPorRegionDTO.
     */
    @GetMapping("/region-mayor-puntuacion")
    public ResponseEntity<PuntuacionPorRegionDTO> obtenerRegionMayorPuntuacion() {
        PuntuacionPorRegionDTO dto = resultadoConsultaService.obtenerRegionMayorPuntuacion();
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}
