package com.concursoacm.interfaces.services;

import com.concursoacm.application.dtos.pais.PaisDTO;
import com.concursoacm.models.Pais;

import java.util.List;
import java.util.Optional;

/**
 * *Interfaz que define los métodos para la gestión de países.
 */
public interface IPaisService {

    /**
     * *Obtiene una lista de todos los países.
     *
     * @return Lista de objetos PaisDTO.
     */
    List<PaisDTO> obtenerTodosLosPaises();

    /**
     * *Obtiene un país por su ID.
     *
     * @param id ID del país.
     * @return Un Optional con el objeto PaisDTO si se encuentra.
     */
    Optional<PaisDTO> obtenerPaisPorId(int id);

    /**
     * *Guarda un nuevo país en la base de datos.
     *
     * @param pais Objeto Pais a guardar.
     * @return Objeto PaisDTO del país guardado.
     */
    PaisDTO guardarPais(Pais pais);

    /**
     * *Actualiza un país existente.
     *
     * @param id   ID del país a actualizar.
     * @param pais Objeto Pais con los nuevos datos.
     * @return Objeto PaisDTO del país actualizado.
     */
    PaisDTO actualizarPais(int id, Pais pais);

    /**
     * *Elimina un país por su ID.
     *
     * @param id ID del país a eliminar.
     * @return true si el país fue eliminado, false en caso contrario.
     */
    String eliminarPais(int id);

    /**
     * *Obtiene una lista de países por su región.
     *
     * @param regionId ID de la región.
     * @return Lista de objetos PaisDTO.
     */
    List<PaisDTO> obtenerPaisesPorRegion(int regionId);
}