package com.concursoacm.controllers;

import com.concursoacm.interfaces.services.IPreguntaService;
import com.concursoacm.models.Pregunta;
import com.concursoacm.models.PreguntaClase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * *Controlador REST para la gestión de preguntas.
 */
@RestController
@RequestMapping("/preguntas")
public class PreguntaController {

    private final IPreguntaService preguntaService;

    public PreguntaController(IPreguntaService preguntaService) {
        this.preguntaService = preguntaService;
    }

    /**
     * *Obtiene todas las preguntas.
     *
     * @return Lista de preguntas.
     */
    @GetMapping
    public List<Pregunta> obtenerTodasLasPreguntas() {
        return preguntaService.obtenerTodasLasPreguntas();
    }

    /**
     * *Obtiene una pregunta por su ID.
     *
     * @param id ID de la pregunta.
     * @return Pregunta encontrada o mensaje de error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPreguntaPorId(@PathVariable int id) {
        try {
            Pregunta pregunta = preguntaService.obtenerPreguntaPorId(id);
            return ResponseEntity.ok(pregunta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * *Crea una nueva pregunta.
     *
     * @param pregunta Pregunta a crear.
     * @return Pregunta creada.
     */
    @PostMapping
    public ResponseEntity<Pregunta> crearPregunta(@RequestBody Pregunta pregunta) {
        Pregunta nuevaPregunta = preguntaService.crearPregunta(pregunta);
        return ResponseEntity.ok(nuevaPregunta);
    }

    /**
     * *Actualiza una pregunta existente.
     *
     * @param id       ID de la pregunta a actualizar.
     * @param pregunta Pregunta con los nuevos datos.
     * @return Pregunta actualizada o mensaje de error.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPregunta(@PathVariable int id, @RequestBody Pregunta pregunta) {
        try {
            Pregunta preguntaActualizada = preguntaService.actualizarPregunta(id, pregunta);
            return ResponseEntity.ok(preguntaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * *Elimina una pregunta por su ID.
     *
     * @param id ID de la pregunta a eliminar.
     * @return Mensaje de éxito o error.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPregunta(@PathVariable int id) {
        boolean eliminada = preguntaService.eliminarPregunta(id);
        if (eliminada) {
            return ResponseEntity.ok("Pregunta eliminada correctamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * *Obtiene todas las clases de preguntas.
     *
     * @return Lista de clases de preguntas.
     */
    @GetMapping("/clases")
    public List<PreguntaClase> obtenerClasesDePreguntas() {
        return preguntaService.obtenerClasesDePreguntas();
    }
}
