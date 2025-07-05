package com.concursoacm.controllers;

import com.concursoacm.application.dtos.jefedelegacion.JefeDelegacionDTO;
import com.concursoacm.application.dtos.jefedelegacion.CrearJefeDelegacionDTO;
import com.concursoacm.application.dtos.jefedelegacion.CambiarContrasenaDTO;
import com.concursoacm.application.dtos.jefedelegacion.JefeDelegacionFiltroDTO;
import com.concursoacm.interfaces.services.IJefeDelegacionService;
import com.concursoacm.models.JefeDelegacion;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * *Controlador REST para la gestión de jefes de delegación.
 */
@RestController
@RequestMapping("/jefes-delegacion")
public class JefeDelegacionController {

    private final IJefeDelegacionService jefeDelegacionService;

    /**
     * *Constructor que inyecta el servicio de jefes de delegación.
     *
     * @param jefeDelegacionService Servicio para la gestión de jefes de delegación.
     */
    public JefeDelegacionController(IJefeDelegacionService jefeDelegacionService) {
        this.jefeDelegacionService = jefeDelegacionService;
    }

    /**
     * *Obtiene una lista de todos los jefes de delegación.
     *
     * @return Lista de objetos JefeDelegacionDTO.
     */
    @GetMapping
    public ResponseEntity<List<JefeDelegacionDTO>> obtenerTodosLosJefes() {
        List<JefeDelegacionDTO> jefes = jefeDelegacionService.obtenerTodosLosJefes();
        return ResponseEntity.ok(jefes);
    }

    /**
     * *Obtiene una lista de todos los jefes de delegación con país y región para
     * *filtrado frontend.
     * 
     * @return Lista de objetos JefeDelegacionFiltroDTO.
     */
    @GetMapping("/con-pais-region")
    public ResponseEntity<List<JefeDelegacionFiltroDTO>> obtenerJefesConPaisYRegion() {
        List<JefeDelegacionFiltroDTO> jefes = jefeDelegacionService.obtenerJefesConPaisYRegion();
        return ResponseEntity.ok(jefes);
    }

    /**
     * *Obtiene un jefe de delegación por su ID.
     *
     * @param idJefe ID del jefe de delegación.
     * @return Objeto JefeDelegacionDTO si se encuentra.
     */
    @GetMapping("/{idJefe}")
    public ResponseEntity<JefeDelegacionDTO> obtenerJefePorId(@PathVariable int idJefe) {
        return jefeDelegacionService.obtenerJefePorId(idJefe)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * *Crea un nuevo jefe de delegación.
     *
     * @param request DTO con los datos del nuevo jefe de delegación.
     * @return Objeto JefeDelegacion creado.
     */
    @PostMapping
    public ResponseEntity<JefeDelegacion> crearJefeDelegacion(@Valid @RequestBody CrearJefeDelegacionDTO request) {
        JefeDelegacion nuevoJefe = jefeDelegacionService.crearJefeDelegacion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoJefe);
    }

    /**
     * *Actualiza la contraseña de un jefe de delegación.
     *
     * @param id             ID del jefe de delegación.
     * @param request        DTO con las contraseñas actual y nueva.
     * @param authentication Información del usuario autenticado.
     * @return Mensaje de éxito o error.
     */
    @PutMapping("/{id}/cambiar-contrasena")
    public ResponseEntity<String> cambiarContraseña(
            @PathVariable int id,
            @RequestBody CambiarContrasenaDTO request,
            Authentication authentication) {

        String usuarioNormalizado = authentication.getName();
        boolean cambiada = jefeDelegacionService.cambiarContraseña(
                id,
                usuarioNormalizado,
                request.getContrasenaActual(),
                request.getNuevaContrasena());

        if (cambiada) {
            return ResponseEntity.ok("Contraseña actualizada correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas.");
        }
    }

    /**
     * *Elimina un jefe de delegación por su ID.
     *
     * @param idJefe ID del jefe de delegación a eliminar.
     * @return Mensaje de éxito.
     */
    @DeleteMapping("/{idJefe}")
    public ResponseEntity<String> eliminarJefeDelegacion(@PathVariable int idJefe) {
        jefeDelegacionService.eliminarJefeDelegacion(idJefe);
        return ResponseEntity.ok("Jefe de delegación eliminado correctamente.");
    }

    /**
     * Endpoint para buscar jefes de delegación por nombre, país y región (filtros
     * opcionales).
     * Devuelve una lista de JefeDelegacionFiltroDTO para el frontend.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<JefeDelegacionFiltroDTO>> buscarJefesDelegacion(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer idPais,
            @RequestParam(required = false) Integer idRegion) {
        List<JefeDelegacionFiltroDTO> jefes = jefeDelegacionService.buscarJefesDelegacion(nombre, idPais, idRegion);
        return ResponseEntity.ok(jefes);
    }

    /**
     * *Endpoint para obtener el idPais de un jefe de delegación por su nombre.
     * @param nombre Nombre del jefe de delegación.
     * @return ID del país asociado al jefe de delegación o null si no se encuentra.
     */
    @GetMapping("/pais")
    public ResponseEntity<Integer> obtenerIdPaisPorNombre(@RequestParam String nombre) {
        Integer idPais = jefeDelegacionService.obtenerIdPaisPorNombre(nombre);
        if (idPais != null) {
            return ResponseEntity.ok(idPais);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
