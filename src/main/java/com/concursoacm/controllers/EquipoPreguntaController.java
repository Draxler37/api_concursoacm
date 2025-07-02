package com.concursoacm.controllers;

import com.concursoacm.application.dtos.equipos.EquipoConPreguntasDTO;
import com.concursoacm.application.dtos.equipos.EquiposContadoresDTO;
import com.concursoacm.application.dtos.preguntas.PreguntasAsignadasDetalleDTO;
import com.concursoacm.interfaces.services.IEquipoPreguntaService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * *Controlador REST para la gestión de preguntas asignadas.
 */
@RestController
@RequestMapping("/equipos-preguntas")
public class EquipoPreguntaController {

    private final IEquipoPreguntaService equipoPreguntaService;

    /**
     * *Constructor que inyecta el servicio de preguntas asignadas.
     *
     * @param equipoPreguntaService Servicio para la gestión de preguntas
     *                              asignadas.
     */
    public EquipoPreguntaController(IEquipoPreguntaService equipoPreguntaService) {
        this.equipoPreguntaService = equipoPreguntaService;
    }

    /**
     * *Asigna preguntas a todos los equipos sin asignación previa.
     *
     * @return Mensaje de éxito o error.
     */
    @PostMapping("/asignar-todos")
    public ResponseEntity<String> asignarPreguntasATodos() {
        equipoPreguntaService.asignarPreguntasATodosLosEquipos();
        return ResponseEntity.ok("Preguntas asignadas a todos los equipos sin asignación previa.");
    }

    /**
     * *Obtiene los detalles de las preguntas asignadas a un equipo.
     *
     * @param idEquipo ID del equipo.
     * @return DTO con los detalles de las preguntas asignadas.
     */
    @GetMapping("/equipo/{idEquipo}")
    public ResponseEntity<?> obtenerPreguntasAsignadasAlEquipo(@PathVariable int idEquipo,
            Authentication authentication) {
        try {
            PreguntasAsignadasDetalleDTO dto = equipoPreguntaService.getPreguntasAsignadasAlEquipo(idEquipo,
                    authentication);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).body("Acceso denegado: " + e.getMessage());
        }
    }

    /**
     * Endpoint para buscar equipos con cantidad de preguntas asignadas y filtro de
     * asignación.
     * 
     * @param nombre      Nombre del equipo (opcional)
     * @param idPais      ID del país (opcional)
     * @param idCategoria ID de la categoría (opcional)
     * @param asignado    null=Todos, true=Con preguntas, false=Sin preguntas
     * @return Lista de equipos con cantidad de preguntas asignadas
     */
    @GetMapping("/buscar-equipos-asignacion")
    public ResponseEntity<List<EquipoConPreguntasDTO>> buscarEquiposAsignacion(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer idPais,
            @RequestParam(required = false) Integer idCategoria,
            @RequestParam(required = false) Boolean asignado) {
        List<EquipoConPreguntasDTO> lista = equipoPreguntaService.buscarEquiposConPreguntasAsignadas(nombre, idPais,
                idCategoria, asignado);
        return ResponseEntity.ok(lista);
    }

    /**
     * Endpoint para obtener los contadores de equipos: total, con preguntas y sin
     * preguntas.
     */
    @GetMapping("/contadores")
    public ResponseEntity<EquiposContadoresDTO> obtenerContadoresEquipos() {
        EquiposContadoresDTO dto = equipoPreguntaService.obtenerContadoresEquipos();
        return ResponseEntity.ok(dto);
    }

    /**
     * Asigna preguntas a un equipo específico por su ID.
     * 
     * @param idEquipo ID del equipo
     * @return Mensaje de éxito o error.
     */
    @PostMapping("/asignar/{idEquipo}")
    public ResponseEntity<String> asignarPreguntasAEquipo(@PathVariable int idEquipo) {
        try {
            equipoPreguntaService.asignarPreguntasAEquipoPorId(idEquipo);
            return ResponseEntity.ok("Preguntas asignadas al equipo correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Obtiene las preguntas asignadas al equipo del participante.
     * Si el participante no tiene equipo, devuelve 404 y un mensaje.
     * Si tiene equipo, devuelve las preguntas asignadas a ese equipo.
     */
    @GetMapping("/participante/{idParticipante}")
    public ResponseEntity<?> obtenerPreguntasAsignadasAlEquipoDelParticipante(@PathVariable int idParticipante) {
        try {
            PreguntasAsignadasDetalleDTO dto = equipoPreguntaService
                    .getPreguntasAsignadasDeParticipante(idParticipante);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}