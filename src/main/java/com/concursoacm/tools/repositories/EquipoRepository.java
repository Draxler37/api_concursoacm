package com.concursoacm.tools.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.concursoacm.models.Equipo;

import java.util.List;

/**
 * *Repositorio para la gestión de la entidad Equipo.
 * *Proporciona métodos para realizar operaciones CRUD y consultas
 * *personalizadas.
 */
@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {

    /**
     * *Cuenta el número de equipos asociados a un país y una categoría específicos.
     *
     * @param idPais      ID del país.
     * @param idCategoria ID de la categoría.
     * @return Número de equipos asociados al país y la categoría.
     */
    int countByPaisIdPaisAndEquipoCategoriaIdEquipoCategoria(int idPais, int idCategoria);

    // Devuelve todos los equipos de un país
    List<Equipo> findByPaisIdPais(int idPais);

    /**
     * Busca equipos filtrando por nombre, país y categoría.
     * 
     * @param nombre      Nombre del equipo.
     * @param idPais      ID del país.
     * @param idCategoria ID de la categoría.
     * @return Lista de equipos que coinciden con los criterios de búsqueda.
     */
    @Query("""
                SELECT e FROM Equipo e
                WHERE (:nombre IS NULL OR :nombre = '' OR LOWER(e.nombreEquipo) LIKE LOWER(CONCAT('%', :nombre, '%')))
                AND (:idPais IS NULL OR e.pais.idPais = :idPais)
                AND (:idCategoria IS NULL OR e.equipoCategoria.idEquipoCategoria = :idCategoria)
            """)
    List<Equipo> buscarFiltrado(
            @Param("nombre") String nombre,
            @Param("idPais") Integer idPais,
            @Param("idCategoria") Integer idCategoria);

    /**
     * *Busca equipos junto con la cantidad de preguntas asignadas, filtrando por
     * *nombre, país y categoría.
     *
     * @param nombre      Nombre del equipo.
     * @param idPais      ID del país.
     * @param idCategoria ID de la categoría.
     * @return Lista de objetos donde cada objeto es un arreglo con el equipo y la
     *         cantidad de preguntas asignadas.
     */
    @Query("""
                SELECT e, COUNT(ep) as cantidadPreguntasAsignadas
                FROM Equipo e
                LEFT JOIN EquipoPregunta ep ON ep.equipo.idEquipo = e.idEquipo
                WHERE (:nombre IS NULL OR :nombre = '' OR LOWER(e.nombreEquipo) LIKE LOWER(CONCAT('%', :nombre, '%')))
                AND (:idPais IS NULL OR e.pais.idPais = :idPais)
                AND (:idCategoria IS NULL OR e.equipoCategoria.idEquipoCategoria = :idCategoria)
                GROUP BY e
            """)
    List<Object[]> buscarEquiposConPreguntasAsignadas(
            @Param("nombre") String nombre,
            @Param("idPais") Integer idPais,
            @Param("idCategoria") Integer idCategoria);
}
