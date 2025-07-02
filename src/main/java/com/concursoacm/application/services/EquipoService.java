package com.concursoacm.application.services;

import com.concursoacm.application.dtos.equipos.EquipoDTO;
import com.concursoacm.interfaces.services.IEquipoService;
import com.concursoacm.models.Equipo;
import com.concursoacm.models.EquipoCategoria;
import com.concursoacm.models.Pais;
import com.concursoacm.tools.repositories.EquipoCategoriaRepository;
import com.concursoacm.tools.repositories.EquipoRepository;
import com.concursoacm.tools.repositories.JefeDelegacionRepository;
import com.concursoacm.tools.repositories.PaisRepository;
import com.concursoacm.utils.Constantes;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * *Servicio que implementa la lógica de negocio para la gestión de equipos.
 */
@Service
public class EquipoService implements IEquipoService {

    private final EquipoRepository equipoRepository;
    private final JefeDelegacionRepository jefeDelegacionRepository;
    private final PaisRepository paisRepository;
    private final EquipoCategoriaRepository equipoCategoriaRepository;

    /**
     * *Constructor que inyecta las dependencias necesarias.
     *
     * @param equipoRepository         Repositorio para la gestión de equipos.
     * @param jefeDelegacionRepository Repositorio para la gestión de jefes de
     *                                 delegación.
     * @param paisRepository           Repositorio para la gestión de países.
     */
    public EquipoService(EquipoRepository equipoRepository, JefeDelegacionRepository jefeDelegacionRepository,
            PaisRepository paisRepository, EquipoCategoriaRepository equipoCategoriaRepository) {
        this.equipoRepository = equipoRepository;
        this.jefeDelegacionRepository = jefeDelegacionRepository;
        this.paisRepository = paisRepository;
        this.equipoCategoriaRepository = equipoCategoriaRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EquipoDTO> getAllEquipos() {
        return equipoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EquipoDTO getEquipoById(int id) {
        return equipoRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado con ID " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EquipoDTO guardarEquipo(Equipo equipo, String usuarioNormalizado) {
        validarPermisosDeJefe(equipo, usuarioNormalizado);
        validarRestriccionesPorCategoria(equipo);
        Equipo equipoGuardado = equipoRepository.save(equipo);
        return convertToDto(equipoGuardado);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eliminarEquipo(int idEquipo, String usuarioNormalizado) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado con ID " + idEquipo));
        validarPermisosDeJefe(equipo, usuarioNormalizado);
        equipoRepository.deleteById(idEquipo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EquipoDTO> getEquiposPorPais(int paisId) {
        return equipoRepository.findByPaisIdPais(paisId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EquipoDTO> buscarEquipos(String nombre, Integer idPais, Integer idCategoria) {
        return equipoRepository.buscarFiltrado(nombre, idPais, idCategoria)
                .stream()
                .map(this::convertToDto)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * *Valida que el jefe de delegación tenga permisos para gestionar el equipo.
     *
     * @param equipo             Objeto Equipo.
     * @param usuarioNormalizado Usuario autenticado.
     */
    private void validarPermisosDeJefe(Equipo equipo, String usuarioNormalizado) {
        int idParticipanteJefe = jefeDelegacionRepository.findByUsuarioNormalizado(usuarioNormalizado)
                .map(jefe -> jefe.getParticipante().getIdParticipante())
                .orElseThrow(() -> new SecurityException("Usuario no encontrado como jefe de delegación."));

        String paisJefe = jefeDelegacionRepository.findPaisByParticipanteId(idParticipanteJefe)
                .orElseThrow(() -> new SecurityException("No tienes un país asignado."));

        if (equipo.getIdEquipo() != 0) { // Validar actualización
            Equipo equipoExistente = equipoRepository.findById(equipo.getIdEquipo())
                    .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado."));
            if (!equipoExistente.getPais().getNombrePais().equalsIgnoreCase(paisJefe)) {
                throw new SecurityException("No puedes modificar equipos de un país diferente al tuyo.");
            }
            equipo.setPais(equipoExistente.getPais());
        } else { // Validar creación
            Pais paisCompleto = paisRepository.findById(equipo.getPais().getIdPais())
                    .orElseThrow(() -> new IllegalArgumentException("El país especificado no existe."));
            if (!paisCompleto.getNombrePais().equalsIgnoreCase(paisJefe)) {
                throw new SecurityException("No puedes crear equipos para un país diferente al tuyo.");
            }
            EquipoCategoria equipoCategoria = equipoCategoriaRepository
                    .findById(equipo.getEquipoCategoria().getIdEquipoCategoria())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));

            equipo.setPais(paisCompleto);
            equipo.setEquipoCategoria(equipoCategoria);
        }
    }

    /**
     * *Valida las restricciones de categoría para los equipos.
     *
     * @param equipo Objeto Equipo.
     */
    private void validarRestriccionesPorCategoria(Equipo equipo) {
        int idPais = equipo.getPais().getIdPais();
        String categoriaNombre = equipoCategoriaRepository.findById(equipo.getEquipoCategoria().getIdEquipoCategoria())
                .map(categoria -> categoria.getNombreCategoria())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));

        if (categoriaNombre.equalsIgnoreCase(Constantes.CATEGORIA_COMPETENCIA)) {
            int countCompetencia = equipoRepository.countByPaisIdPaisAndEquipoCategoriaIdEquipoCategoria(idPais,
                    equipo.getEquipoCategoria().getIdEquipoCategoria());
            if (countCompetencia >= 2) {
                throw new IllegalArgumentException("Ya existen 2 equipos de competencia para este país.");
            }
        } else if (categoriaNombre.equalsIgnoreCase(Constantes.CATEGORIA_JUNIOR)) {
            int countJunior = equipoRepository.countByPaisIdPaisAndEquipoCategoriaIdEquipoCategoria(idPais,
                    equipo.getEquipoCategoria().getIdEquipoCategoria());
            if (countJunior >= 1) {
                throw new IllegalArgumentException("Ya existe un equipo Junior para este país.");
            }
        }
    }

    /**
     * *Convierte un objeto Equipo en un EquipoDTO.
     *
     * @param equipo Objeto Equipo.
     * @return Objeto EquipoDTO.
     */
    private EquipoDTO convertToDto(Equipo equipo) {
        return new EquipoDTO(
                equipo.getIdEquipo(),
                equipo.getNombreEquipo(),
                equipo.getEquipoCategoria().getNombreCategoria(),
                equipo.getPais().getNombrePais());
    }
}
