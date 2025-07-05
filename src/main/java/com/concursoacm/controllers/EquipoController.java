package com.concursoacm.controllers;

import com.concursoacm.application.dtos.equipos.EquipoCategoriaDTO;
import com.concursoacm.application.dtos.equipos.EquipoDTO;
import com.concursoacm.interfaces.services.IEquipoService;
import com.concursoacm.models.Equipo;
import com.concursoacm.models.EquipoCategoria;
import com.concursoacm.tools.repositories.EquipoCategoriaRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 * *Controlador REST para la gestión de equipos.
 */
@RestController
@RequestMapping("/equipos")
public class EquipoController {

    private final IEquipoService equipoService;

    @Autowired
    private EquipoCategoriaRepository equipoCategoriaRepository;

    /**
     * *Constructor que inyecta el servicio de equipos.
     *
     * @param equipoService Servicio para la gestión de equipos.
     */
    public EquipoController(IEquipoService equipoService) {
        this.equipoService = equipoService;
    }

    /**
     * *Obtiene una lista de todos los equipos.
     *
     * @return Lista de objetos EquipoDTO.
     */
    @GetMapping
    public ResponseEntity<List<EquipoDTO>> getAllEquipos() {
        List<EquipoDTO> equipos = equipoService.getAllEquipos();
        return ResponseEntity.ok(equipos);
    }

    /**
     * *Obtiene un equipo por su ID.
     *
     * @param id ID del equipo.
     * @return Objeto EquipoDTO si se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EquipoDTO> getEquipoById(@PathVariable int id) {
        EquipoDTO equipo = equipoService.getEquipoById(id);
        return ResponseEntity.ok(equipo);
    }

    /**
     * *Crea un nuevo equipo (solo jefes de delegación autenticados).
     *
     * @param equipo         Objeto Equipo a crear.
     * @param authentication Información del usuario autenticado.
     * @return Objeto EquipoDTO del equipo creado.
     */
    @PostMapping
    public ResponseEntity<EquipoDTO> crearEquipo(@Valid @RequestBody Equipo equipo, Authentication authentication) {
        String usuarioNormalizado = authentication.getName();
        EquipoDTO nuevoEquipo = equipoService.guardarEquipo(equipo, usuarioNormalizado);
        return ResponseEntity.ok(nuevoEquipo);
    }

    /**
     * *Actualiza un equipo existente (solo jefes de delegación autenticados).
     *
     * @param id             ID del equipo a actualizar.
     * @param equipo         Objeto Equipo con los nuevos datos.
     * @param authentication Información del usuario autenticado.
     * @return Objeto EquipoDTO actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EquipoDTO> actualizarEquipo(@PathVariable int id, @Valid @RequestBody Equipo equipo,
            Authentication authentication) {
        equipo.setIdEquipo(id);
        String usuarioNormalizado = authentication.getName();
        EquipoDTO equipoActualizado = equipoService.guardarEquipo(equipo, usuarioNormalizado);
        return ResponseEntity.ok(equipoActualizado);
    }

    /**
     * *Elimina un equipo (solo jefes de delegación).
     *
     * @param id             ID del equipo a eliminar.
     * @param authentication Información del usuario autenticado.
     * @return Mensaje de éxito.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEquipo(@PathVariable int id, Authentication authentication) {
        String usuarioNormalizado = authentication.getName();
        equipoService.eliminarEquipo(id, usuarioNormalizado);
        return ResponseEntity.ok("Equipo eliminado correctamente.");
    }

    /**
     * *Obtiene una lista de equipos filtrados por país.
     *
     * @param paisId ID del país por el que filtrar los equipos.
     * @return Lista de objetos EquipoDTO que pertenecen al país especificado.
     */
    @GetMapping(params = "paisId")
    public ResponseEntity<List<EquipoDTO>> getEquiposPorPais(@RequestParam("paisId") int paisId) {
        List<EquipoDTO> equipos = equipoService.getEquiposPorPais(paisId);
        return ResponseEntity.ok(equipos);
    }

    /**
     * Endpoint para obtener la lista de categorías de equipos.
     * 
     * @return Lista de EquipoCategoriaDTO
     */
    @GetMapping("/categorias")
    public ResponseEntity<List<EquipoCategoriaDTO>> getCategoriasEquipos() {
        List<EquipoCategoria> categorias = equipoCategoriaRepository.findAll();
        List<EquipoCategoriaDTO> dtos = categorias.stream()
                .map(cat -> new EquipoCategoriaDTO(cat.getIdEquipoCategoria(), cat.getNombreCategoria()))
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * *Búsqueda flexible de equipos por nombre, país y/o categoría.
     * 
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<EquipoDTO>> buscarEquipos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer idPais,
            @RequestParam(required = false) Integer idCategoria) {
        List<EquipoDTO> resultado = equipoService.buscarEquipos(nombre, idPais, idCategoria);
        return ResponseEntity.ok(resultado);
    }
}
