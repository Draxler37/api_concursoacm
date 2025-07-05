package com.concursoacm.tools.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.concursoacm.models.Participante;

/**
 * *Repositorio para la gestión de la entidad Participante.
 * *Proporciona métodos para realizar operaciones CRUD y consultas
 * *personalizadas.
 */
@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Integer> {

        /**
         * *Obtiene una lista de participantes filtrados por el ID de la región (a
         * *través del país).
         *
         * @param idRegion ID de la región.
         * @return Lista de participantes en la región.
         */
        List<Participante> findByPaisRegionIdRegion(int idRegion);

        /**
         * *Obtiene una lista de participantes filtrados por el ID del país.
         *
         * @param idPais ID del país.
         * @return Lista de participantes en el país.
         */
        List<Participante> findByPaisIdPais(int idPais);

        /**
         * *Obtiene una lista de participantes que pertenecen a un equipo específico.
         *
         * @param idEquipo ID del equipo.
         * @return Lista de participantes en el equipo.
         */
        List<Participante> findByEquipoIdEquipo(int idEquipo);

        /**
         * *Cuenta el número de participantes asociados a un país específico.
         *
         * @param idPais ID del país.
         * @return Número de participantes en el país.
         */
        int countByPaisIdPais(int idPais);

        /**
         * *Cuenta el número de participantes asociados a un equipo específico.
         *
         * @param idEquipo ID del equipo.
         * @return Número de participantes en el equipo.
         */
        int countByEquipoIdEquipo(int idEquipo);

        /**
         * *Busca participantes filtrando por nombre, país, equipo y región.
         *
         * @param nombre   Nombre del participante.
         * @param idPais   ID del país.
         * @param idEquipo ID del equipo.
         * @param idRegion ID de la región.
         * @return Lista de participantes que coinciden con los criterios de búsqueda.
         */
        @Query("""
                        SELECT p FROM Participante p
                        WHERE (:nombre IS NULL OR :nombre = '' OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
                        AND (:idPais IS NULL OR p.pais.idPais = :idPais)
                        AND (:idEquipo IS NULL OR p.equipo.idEquipo = :idEquipo)
                        AND (:idRegion IS NULL OR p.pais.region.idRegion = :idRegion)
                        """)
        List<Participante> buscarFiltrado(
                        @Param("nombre") String nombre,
                        @Param("idPais") Integer idPais,
                        @Param("idEquipo") Integer idEquipo,
                        @Param("idRegion") Integer idRegion);

        /**
         * Verifica si existe un participante con el nombre dado (excluyendo jefes de
         * delegación).
         * 
         * @param nombre Nombre del participante
         * @return ID del participante si existe y NO es jefe de delegación.
         */
        @Query("""
                        SELECT p.idParticipante FROM Participante p
                        WHERE LOWER(p.nombre) = LOWER(:nombre)
                        AND p.idParticipante NOT IN (SELECT j.participante.idParticipante FROM JefeDelegacion j)
                        """)
        Optional<Integer> findIdByNombreIgnoreCaseAndNotJefeDelegacion(@Param("nombre") String nombre);

        /**
         * * Obtiene los participantes que no están asignados a ningún equipo por el
         * 
         * @param idPais ID del país para filtrar los participantes.
         * @return Lista de participantes sin equipo asignado en el país.
         */
        @Query("SELECT p FROM Participante p WHERE p.pais.idPais = :idPais AND p.equipo IS NULL")
        List<Participante> findByPaisIdPaisAndEquipoIsNull(@Param("idPais") int idPais);
}
