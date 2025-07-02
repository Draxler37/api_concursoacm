package com.concursoacm.interfaces.services;

import com.concursoacm.application.dtos.equipos.EquipoDTO;
import com.concursoacm.models.Equipo;

import java.util.List;

/**
 * *Interfaz que define los métodos para la gestión de equipos.
 */
public interface IEquipoService {

    /**
     * *Obtiene una lista de todos los equipos.
     *
     * @return Lista de objetos EquipoDTO.
     */
    List<EquipoDTO> getAllEquipos();

    /**
     * *Obtiene un equipo por su ID.
     *
     * @param id ID del equipo.
     * @return Objeto EquipoDTO si se encuentra.
     */
    EquipoDTO getEquipoById(int id);

    /**
     * *Guarda un equipo (creación o actualización).
     *
     * @param equipo             Objeto Equipo a guardar.
     * @param usuarioNormalizado Usuario autenticado.
     * @return Objeto EquipoDTO del equipo guardado.
     */
    EquipoDTO guardarEquipo(Equipo equipo, String usuarioNormalizado);

    /**
     * *Elimina un equipo por su ID.
     *
     * @param idEquipo           ID del equipo a eliminar.
     * @param usuarioNormalizado Usuario autenticado.
     */
    void eliminarEquipo(int idEquipo, String usuarioNormalizado);

    /**
     * *Obtiene una lista de equipos por el ID de su país.
     *
     * @param paisId ID del país.
     * @return Lista de objetos EquipoDTO.
     */
    List<EquipoDTO> getEquiposPorPais(int paisId);

    /**
     * Búsqueda flexible de equipos por nombre, país y/o categoría.
     * Todos los parámetros son opcionales.
     */
    List<EquipoDTO> buscarEquipos(String nombre, Integer idPais, Integer idCategoria);
}