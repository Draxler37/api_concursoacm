package com.concursoacm.controllers;

import com.concursoacm.application.dtos.pais.PaisDTO;
import com.concursoacm.interfaces.services.IPaisService;
import com.concursoacm.models.Pais;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * *Controlador REST para la gestión de países.
 */
@RestController
@RequestMapping("/paises")
public class PaisController {

    private final IPaisService paisService;

    /**
     * *Constructor que inyecta el servicio de países.
     *
     * @param paisService Servicio para la gestión de países.
     */
    public PaisController(IPaisService paisService) {
        this.paisService = paisService;
    }

    /**
     * *Obtiene una lista de todos los países.
     *
     * @return Lista de objetos PaisDTO.
     */
    @GetMapping
    public List<PaisDTO> obtenerTodosLosPaises() {
        return paisService.obtenerTodosLosPaises();
    }

    /**
     * *Obtiene un país por su ID.
     *
     * @param id ID del país.
     * @return Un Optional con el objeto PaisDTO si se encuentra.
     */
    @GetMapping("/{id}")
    public Optional<PaisDTO> obtenerPaisPorId(@PathVariable int id) {
        return paisService.obtenerPaisPorId(id);
    }

    /**
     * *Crea un nuevo país.
     *
     * @param pais Objeto Pais a crear.
     * @return Objeto PaisDTO del país creado.
     */
    @PostMapping
    public PaisDTO crearPais(@Valid @RequestBody Pais pais) {
        return paisService.guardarPais(pais);
    }

    /**
     * *Actualiza un país existente.
     *
     * @param id   ID del país a actualizar.
     * @param pais Objeto Pais con los nuevos datos.
     * @return Objeto PaisDTO del país actualizado.
     */
    @PutMapping("/{id}")
    public PaisDTO actualizarPais(@PathVariable int id, @Valid @RequestBody Pais pais) {
        return paisService.actualizarPais(id, pais);
    }

    /**
     * *Elimina un país por su ID.
     *
     * @param id ID del país a eliminar.
     * @return true si el país fue eliminado, false en caso contrario.
     */
    @DeleteMapping("/{id}")
    public String eliminarPais(@PathVariable int id) {
        return paisService.eliminarPais(id);
    }

    /**
     * *Obtiene una lista de países por su región.
     *
     * @param regionId ID de la región.
     * @return Lista de objetos PaisDTO pertenecientes a la región.
     */
    @GetMapping(params = "regionId")
    public List<PaisDTO> obtenerPaisesPorRegion(@RequestParam("regionId") int regionId) {
        return paisService.obtenerPaisesPorRegion(regionId);
    }
}
