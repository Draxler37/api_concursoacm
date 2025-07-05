package com.concursoacm.application.services;

import com.concursoacm.application.dtos.pais.PaisDTO;
import com.concursoacm.application.dtos.participantes.ParticipanteDTO;
import com.concursoacm.application.dtos.participantes.ParticipantesPorPaisDTO;
import com.concursoacm.application.dtos.participantes.ParticipantesPorRegionDTO;
import com.concursoacm.application.dtos.participantes.ParticipantesPorEquipoDTO;
import com.concursoacm.interfaces.services.IParticipanteService;
import com.concursoacm.models.Equipo;
import com.concursoacm.models.Pais;
import com.concursoacm.models.Participante;
import com.concursoacm.models.Region;
import com.concursoacm.tools.repositories.EquipoRepository;
import com.concursoacm.tools.repositories.JefeDelegacionRepository;
import com.concursoacm.tools.repositories.PaisRepository;
import com.concursoacm.tools.repositories.ParticipanteRepository;
import com.concursoacm.tools.repositories.RegionRepository;
import com.concursoacm.utils.Constantes;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * *Servicio que implementa la lógica de negocio para la gestión de
 * participantes.
 */
@Service
public class ParticipanteService implements IParticipanteService {

    private final ParticipanteRepository participanteRepository;
    private final PaisService paisService;
    private final EquipoRepository equipoRepository;
    private final PaisRepository paisRepository;
    private final RegionRepository regionRepository;
    private final JefeDelegacionRepository jefeDelegacionRepository;

    /**
     * *Constructor que inyecta las dependencias necesarias.
     *
     * @param participanteRepository   Repositorio para la gestión de participantes.
     * @param paisService              Servicio para la gestión de países.
     * @param equipoRepository         Repositorio para la gestión de equipos.
     * @param regionRepository         Repositorio para la gestión de regiones.
     * @param jefeDelegacionRepository Repositorio para la gestión de jefes de
     *                                 delegación.
     */

    public ParticipanteService(ParticipanteRepository participanteRepository,
            PaisService paisService,
            EquipoRepository equipoRepository, RegionRepository regionRepository,
            PaisRepository paisRepository,
            JefeDelegacionRepository jefeDelegacionRepository) {
        this.participanteRepository = participanteRepository;
        this.paisService = paisService;
        this.equipoRepository = equipoRepository;
        this.paisRepository = paisRepository;
        this.regionRepository = regionRepository;
        this.jefeDelegacionRepository = jefeDelegacionRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ParticipanteDTO> getAllParticipantes() {
        return participanteRepository.findAll().stream()
                .map(this::convertirAParticipanteDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipantesPorPaisDTO getParticipantesPorPaisDTO(int idPais) {
        Pais pais = paisRepository.findById(idPais)
                .orElseThrow(() -> new IllegalArgumentException("El pais con ID " + idPais + " no existe."));

        List<ParticipanteDTO> participanteDTO = participanteRepository.findByPaisIdPais(idPais).stream()
                .map(this::convertirAParticipanteDTO).collect(Collectors.toList());

        return new ParticipantesPorPaisDTO(pais.getNombrePais(), participanteDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipantesPorRegionDTO getParticipantesPorRegionDTO(int idRegion) {
        Region region = regionRepository.findById(idRegion)
                .orElseThrow(() -> new IllegalArgumentException("La region con ID " + idRegion + " no existe."));

        List<ParticipanteDTO> participanteDTO = participanteRepository.findByPaisRegionIdRegion(idRegion).stream()
                .map(this::convertirAParticipanteDTO).collect(Collectors.toList());

        return new ParticipantesPorRegionDTO(region.getNombreRegion(), participanteDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipantesPorEquipoDTO getParticipantesPorEquipoDTO(int idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new IllegalArgumentException("El equipo con ID " + idEquipo + " no existe."));

        List<ParticipanteDTO> participanteDTO = participanteRepository.findByEquipoIdEquipo(idEquipo).stream()
                .map(this::convertirAParticipanteDTO)
                .collect(Collectors.toList());

        return new ParticipantesPorEquipoDTO(equipo.getNombreEquipo(), participanteDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipanteDTO getParticipanteById(int id) {
        return participanteRepository.findById(id)
                .map(this::convertirAParticipanteDTO)
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipanteDTO crearParticipante(Participante participante) {
        Pais pais = paisRepository.findById(participante.getPais().getIdPais())
                .orElseThrow(() -> new RuntimeException("País no encontrado"));
        participante.setPais(pais);

        int count = participanteRepository.countByPaisIdPais(participante.getPais().getIdPais());
        if (count >= 15) {
            throw new IllegalArgumentException("Ya se alcanzó el máximo de 15 participantes para este país.");
        }

        Participante nuevoParticipante = participanteRepository.save(participante);
        return convertirAParticipanteDTO(nuevoParticipante);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipanteDTO asignarAlEquipo(int idParticipante, int idEquipo, String usuarioNormalizado) {
        Participante participante = buscarParticipantePorId(idParticipante);
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new IllegalArgumentException("El equipo con ID " + idEquipo + " no existe."));

        // Validar que el jefe de delegacion autentificado no pueda ser asignado a un
        // equipo
        if (jefeDelegacionRepository.existsByParticipanteIdParticipante(idParticipante)) {
            throw new IllegalArgumentException(
                    "El jefe de delegación no puede ser asignado como participante a un equipo.");
        }

        // Validar que el jefe de delegación autenticado solo pueda asignar
        // participantes de su país
        String paisJefe = obtenerPaisDadoUsuario(usuarioNormalizado);
        if (!paisJefe.equalsIgnoreCase(participante.getPais().getNombrePais())) {
            throw new SecurityException("No tienes permiso para asignar participantes de otro país.");
        }

        // Validar que el equipo pertenezca al mismo país que el participante
        if (participante.getPais().getIdPais() != equipo.getPais().getIdPais()) {
            throw new IllegalArgumentException("El equipo no pertenece al mismo país que el participante.");
        }

        // Validar que el participante no esté asignado a otro equipo
        if (participante.getEquipo() != null) {
            throw new IllegalArgumentException("El participante ya está asignado al equipo: "
                    + participante.getEquipo().getNombreEquipo());
        }

        // Validar que los menores de edad solo puedan estar en equipos "Junior"
        if (participante.getEdad() < 18
                && !Constantes.CATEGORIA_JUNIOR.equalsIgnoreCase(equipo.getEquipoCategoria().getNombreCategoria())) {
            throw new IllegalArgumentException(
                    "Los participantes menores de edad solo pueden estar en equipos Junior.");
        }

        // Validar que los mayores de edad solo puedan estar en equipos "Competencia"
        if (participante.getEdad() >= 18
                && !Constantes.CATEGORIA_COMPETENCIA
                        .equalsIgnoreCase(equipo.getEquipoCategoria().getNombreCategoria())) {
            throw new IllegalArgumentException(
                    "Los participantes mayores de edad solo pueden estar en equipos Competencia.");
        }

        // Validar que no haya más de 3 participantes en el equipo
        int participantesEnEquipo = participanteRepository.countByEquipoIdEquipo(idEquipo);
        if (participantesEnEquipo >= 3) {
            throw new IllegalArgumentException("El equipo ya tiene 3 participantes asignados.");
        }

        participante.setEquipo(equipo);
        participanteRepository.save(participante);

        return convertirAParticipanteDTO(participante);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipanteDTO quitarDelEquipo(int idParticipante, int idEquipo, String usuarioNormalizado) {
        Participante participante = buscarParticipantePorId(idParticipante);

        if (!equipoRepository.findById(idEquipo).isPresent()) {
            throw new IllegalArgumentException("El equipo con ID " + idEquipo + " no existe.");
        }

        // Validar que el participante tenga un equipo asignado
        if (participante.getEquipo() == null) {
            throw new IllegalArgumentException("El participante no tiene un equipo asignado.");
        }

        // Validar que el equipo asignado al participante coincida con el ID del equipo
        // proporcionado
        if (participante.getEquipo().getIdEquipo() != idEquipo) {
            throw new IllegalArgumentException("El participante no está asignado al equipo con ID " + idEquipo + ".");
        }

        // Validar que el jefe de delegación autenticado solo pueda quitar participantes
        // de su país
        String paisJefe = obtenerPaisDadoUsuario(usuarioNormalizado);
        if (!paisJefe.equalsIgnoreCase(participante.getPais().getNombrePais())) {
            throw new SecurityException("No tienes permiso para quitar participantes de otro país.");
        }

        participante.setEquipo(null); // Quitar el equipo
        participanteRepository.save(participante);

        return convertirAParticipanteDTO(participante);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipanteDTO updateParticipante(int id, Participante participante) {
        Participante existente = participanteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participante no encontrado con ID " + id));

        existente.setNombre(participante.getNombre());
        existente.setNumCarnet(participante.getNumCarnet());
        existente.setEdad(participante.getEdad());
        existente.setSexo(participante.getSexo());

        if (participante.getPais() != null) {
            PaisDTO paisDTO = paisService.obtenerPaisPorId(participante.getPais().getIdPais())
                    .orElseThrow(() -> new RuntimeException("País no encontrado"));
            Pais pais = new Pais();
            pais.setIdPais(paisDTO.getIdPais());
            pais.setNombrePais(paisDTO.getNombrePais());
            existente.setPais(pais);
        }

        Participante actualizado = participanteRepository.save(existente);
        return convertirAParticipanteDTO(actualizado);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteParticipante(int id) {
        if (participanteRepository.existsById(id)) {
            participanteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ParticipanteDTO> buscarParticipantes(String nombre, Integer idPais, Integer idEquipo,
            Integer idRegion) {
        return participanteRepository.buscarFiltrado(nombre, idPais, idEquipo, idRegion)
                .stream()
                .map(this::convertirAParticipanteDTO)
                .collect(Collectors.toList());
    }

    /**
     * *Busca un participante por su ID.
     *
     * @param idParticipante int idParticipante.
     * @return Objeto Participante.
     */
    private Participante buscarParticipantePorId(int idParticipante) {
        return participanteRepository.findById(idParticipante)
                .orElseThrow(
                        () -> new IllegalArgumentException("El participante con ID " + idParticipante + " no existe."));
    }

    /**
     * *Convierte un objeto Participante en un ParticipanteDTO.
     *
     * @param participante Objeto Participante.
     * @return Objeto ParticipanteDTO.
     */

    private ParticipanteDTO convertirAParticipanteDTO(Participante participante) {
        return new ParticipanteDTO(
                participante.getIdParticipante(),
                participante.getNombre(),
                participante.getNumCarnet(),
                participante.getEdad(),
                participante.getSexo(),
                participante.getPais().getNombrePais(),
                participante.getEquipo() != null ? participante.getEquipo().getNombreEquipo() : "Sin equipo");
    }

    /**
     * *Obtiene el nombre del país relacionado con el usuario normalizado.
     * 
     * @param usuarioNormalizado Usuario autenticado.
     * @return Nombre del país relacionado.
     */
    private String obtenerPaisDadoUsuario(String usuarioNormalizado) {
        return jefeDelegacionRepository.findByUsuarioNormalizado(usuarioNormalizado)
                .map(jefe -> jefe.getParticipante().getPais().getNombrePais())
                .orElseThrow(() -> new SecurityException(Constantes.ERROR_USUARIO_NO_ENCONTRADO));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer obtenerIdPorNombre(String nombre) {
        return participanteRepository.findIdByNombreIgnoreCaseAndNotJefeDelegacion(nombre)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe un participante con el nombre: " + nombre + " o es jefe de delegación."));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ParticipanteDTO> getParticipantesSinEquipoPorPais(int idPais) {
        paisRepository.findById(idPais)
                .orElseThrow(() -> new IllegalArgumentException("El país con ID " + idPais + " no existe."));

        List<Participante> participantesSinEquipo = participanteRepository
                .findByPaisIdPaisAndEquipoIsNull(idPais);

        return participantesSinEquipo.stream()
                .map(this::convertirAParticipanteDTO)
                .collect(Collectors.toList());
    }


}
