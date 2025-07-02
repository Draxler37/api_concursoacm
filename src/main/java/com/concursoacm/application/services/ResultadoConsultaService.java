package com.concursoacm.application.services;

import com.concursoacm.application.dtos.preguntas.PreguntaDTO;
import com.concursoacm.application.dtos.resultados.PuntuacionPorCategoriaDTO;
import com.concursoacm.application.dtos.resultados.PuntuacionPorEquipoDTO;
import com.concursoacm.application.dtos.resultados.PuntuacionPorPaisDTO;
import com.concursoacm.application.dtos.resultados.PuntuacionPorRegionDTO;
import com.concursoacm.application.dtos.resultados.ResultadoDTO;
import com.concursoacm.interfaces.services.IResultadoConsultaService;
import com.concursoacm.utils.Constantes;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * *Clase que implementa la interfaz IResultadoConsultaService y proporciona
 * *métodos para consultar resultados en la base de datos.
 */
@Service
public class ResultadoConsultaService implements IResultadoConsultaService {

    private final JdbcTemplate jdbcTemplate;

    public ResultadoConsultaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "unused", "deprecation" })
    @Override
    public ResultadoDTO obtenerResultadoPorParticipante(int idParticipante) {
        try {
            String sql = "SELECT id_participante, puntuacion_total FROM vista_resultados WHERE id_participante = ?";
            return jdbcTemplate.queryForObject(sql, new Object[] { idParticipante }, (rs, rowNum) -> new ResultadoDTO(
                    rs.getInt("id_participante"),
                    rs.getInt("puntuacion_total")));
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException(
                    "No se encontró resultado para el participante con ID " + idParticipante);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "deprecation", "unused" })
    @Override
    public List<PuntuacionPorEquipoDTO> obtenerPuntuacionesPorCategoria(String categoria) {
        String sql = """
                    SELECT e.nombre_equipo AS nombreEquipo, SUM(vr.puntuacion_total) AS puntuacion_total
                    FROM vista_resultados vr
                    JOIN participantes p ON p.id_participante = vr.id_participante
                    JOIN equipos e ON e.ID_Equipo = p.id_equipo
                    JOIN equipos_categorias ec ON ec.ID_Equipo_Categoria = e.id_equipo_categoria
                    WHERE ec.nombre_categoria = ?
                    GROUP BY e.ID_Equipo, e.nombre_equipo
                    ORDER BY puntuacion_total DESC
                """;
        return jdbcTemplate.query(sql, new Object[] { categoria }, (rs, rowNum) -> new PuntuacionPorEquipoDTO(
                rs.getString("nombreEquipo"),
                rs.getInt("puntuacion_total")));
    }

    /**
     * {@inheritDoc}
     */
    public PuntuacionPorCategoriaDTO obtenerGanadores() {
        List<PuntuacionPorEquipoDTO> competencia = obtenerPuntuacionesPorCategoria(Constantes.CATEGORIA_COMPETENCIA);
        List<PuntuacionPorEquipoDTO> junior = obtenerPuntuacionesPorCategoria(Constantes.CATEGORIA_JUNIOR);

        PuntuacionPorEquipoDTO ganadorCompetencia = competencia.isEmpty() ? null : competencia.get(0);
        PuntuacionPorEquipoDTO ganadorJunior = junior.isEmpty() ? null : junior.get(0);

        return new PuntuacionPorCategoriaDTO(ganadorCompetencia, ganadorJunior);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unused")
    public List<PreguntaDTO> obtenerTop3PreguntasJunior() {
        String sql = """
                    SELECT p.ID_Pregunta AS ID_Pregunta, p.texto AS texto, SUM(r.puntuacion_obtenida) AS puntuacion_total
                    FROM respuestas r
                    JOIN preguntas p ON p.ID_Pregunta = r.id_pregunta
                    JOIN participantes par ON par.ID_Participante = r.id_participante
                    JOIN equipos e ON e.ID_Equipo = par.id_equipo
                    JOIN equipos_categorias ec ON ec.ID_Equipo_Categoria = e.id_equipo_categoria
                    WHERE ec.nombre_categoria = 'Junior'
                    GROUP BY p.ID_Pregunta, p.texto
                    ORDER BY puntuacion_total DESC
                    LIMIT 3
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new PreguntaDTO(
                rs.getInt("ID_Pregunta"),
                rs.getString("texto"),
                rs.getInt("puntuacion_total")));
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unused")
    public PuntuacionPorPaisDTO obtenerPaisMayorPuntuacion() {
        String sql = """
                    SELECT pa.nombre_pais AS nombrePais, SUM(vr.puntuacion_total) AS puntuacion_total
                    FROM vista_resultados vr
                    JOIN participantes p ON p.ID_Participante = vr.id_participante
                    JOIN equipos e ON e.ID_Equipo = p.id_equipo
                    JOIN paises pa ON pa.ID_Pais = e.id_pais
                    GROUP BY pa.ID_Pais, pa.nombre_pais
                    ORDER BY puntuacion_total DESC
                    LIMIT 1
                """;

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new PuntuacionPorPaisDTO(
                    rs.getString("nombrePais"),
                    rs.getInt("puntuacion_total"))).stream().findFirst().orElse(null);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No hay ningún país con resultados en la vista.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unused")
    public PuntuacionPorRegionDTO obtenerRegionMayorPuntuacion() {
        String sql = """
                    SELECT reg.nombre_region AS nombreRegion, SUM(vr.puntuacion_total) AS puntuacion_total
                    FROM vista_resultados vr
                    JOIN participantes par ON par.ID_Participante = vr.id_participante
                    JOIN equipos e ON e.ID_Equipo = par.id_equipo
                    JOIN paises pa ON pa.ID_Pais = e.id_pais
                    JOIN regiones reg ON reg.ID_Region = pa.ID_Region
                    GROUP BY reg.ID_Region, reg.nombre_region
                    ORDER BY puntuacion_total DESC
                    LIMIT 1
                """;

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new PuntuacionPorRegionDTO(
                    rs.getString("nombreRegion"),
                    rs.getInt("puntuacion_total"))).stream().findFirst().orElse(null);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No hay ninguna región con resultados en la vista.");
        }
    }
}
