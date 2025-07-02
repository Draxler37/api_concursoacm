package com.concursoacm.interfaces.services;

import com.concursoacm.application.dtos.jefedelegacion.CrearJefeDelegacionDTO;
import com.concursoacm.application.dtos.jefedelegacion.JefeDelegacionDTO;
import com.concursoacm.application.dtos.jefedelegacion.JefeDelegacionFiltroDTO;
import com.concursoacm.models.JefeDelegacion;

import java.util.List;
import java.util.Optional;

/**
 * *Interfaz que define los métodos para la gestión de jefes de delegación.
 */
public interface IJefeDelegacionService {

    /**
     * *Obtiene una lista de todos los jefes de delegación.
     *
     * @return Lista de objetos JefeDelegacionDTO.
     */
    List<JefeDelegacionDTO> obtenerTodosLosJefes();

    /**
     * *Obtiene un jefe de delegación por su ID.
     *
     * @param idJefe ID del jefe de delegación.
     * @return Un Optional con el objeto JefeDelegacionDTO si se encuentra.
     */
    Optional<JefeDelegacionDTO> obtenerJefePorId(int idJefe);

    /**
     * *Crea un nuevo jefe de delegación.
     *
     * @param idParticipante       ID del participante.
     * @param usuario              Nombre de usuario único.
     * @param contraseñaEncriptada Contraseña encriptada.
     * @return Objeto JefeDelegacion creado.
     */
    JefeDelegacion crearJefeDelegacion(CrearJefeDelegacionDTO crearJefeDelegacionDTO);

    /**
     * *Cambia la contraseña de un jefe de delegación.
     *
     * @param idJefe             ID del jefe de delegación.
     * @param usuarioNormalizado Usuario autenticado.
     * @param contraseñaActual   Contraseña actual.
     * @param nuevaContraseña    Nueva contraseña.
     * @return true si la contraseña fue cambiada, false en caso contrario.
     */
    boolean cambiarContraseña(int idJefe, String usuarioNormalizado, String contraseñaActual, String nuevaContraseña);

    /**
     * *Elimina un jefe de delegación por su ID.
     *
     * @param idJefe ID del jefe de delegación a eliminar.
     */
    void eliminarJefeDelegacion(int idJefe);

    /**
     * Obtiene una lista de todos los jefes de delegación con país y región para
     * filtrado frontend.
     * 
     * @return Lista de objetos JefeDelegacionFiltroDTO.
     */
    List<JefeDelegacionFiltroDTO> obtenerJefesConPaisYRegion();

    /**
     * Busca jefes de delegación por nombre, país y región (filtros opcionales).
     * 
     * @param nombre   Nombre del participante (opcional)
     * @param idPais   ID del país (opcional)
     * @param idRegion ID de la región (opcional)
     * @return Lista de JefeDelegacionFiltroDTO filtrados
     */
    List<JefeDelegacionFiltroDTO> buscarJefesDelegacion(String nombre, Integer idPais, Integer idRegion);
}
