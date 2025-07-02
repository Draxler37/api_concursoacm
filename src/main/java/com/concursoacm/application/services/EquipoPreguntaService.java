package com.concursoacm.application.services;

import com.concursoacm.application.dtos.preguntas.PreguntaDetalleDTO;
import com.concursoacm.application.dtos.preguntas.PreguntasAsignadasDetalleDTO;
import com.concursoacm.application.dtos.equipos.EquipoConPreguntasDTO;
import com.concursoacm.application.dtos.equipos.EquiposContadoresDTO;
import com.concursoacm.interfaces.services.IEquipoPreguntaService;
import com.concursoacm.models.Equipo;
import com.concursoacm.models.EquipoCategoria;
import com.concursoacm.models.EquipoPregunta;
import com.concursoacm.models.JefeDelegacion;
import com.concursoacm.models.Pregunta;
import com.concursoacm.tools.repositories.EquipoPreguntaRepository;
import com.concursoacm.tools.repositories.EquipoRepository;
import com.concursoacm.tools.repositories.JefeDelegacionRepository;
import com.concursoacm.tools.repositories.ParticipanteRepository;
import com.concursoacm.tools.repositories.PreguntaRepository;
import com.concursoacm.utils.Constantes;

import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EquipoPreguntaService implements IEquipoPreguntaService {

    private final EquipoPreguntaRepository equipoPreguntaRepository;
    private final EquipoRepository equipoRepository;
    private final PreguntaRepository preguntaRepository;
    private final JefeDelegacionRepository jefeDelegacionRepository;
    private final ParticipanteRepository participanteRepository;

    public EquipoPreguntaService(EquipoPreguntaRepository equipoPreguntaRepository,
            EquipoRepository equipoRepository,
            PreguntaRepository preguntaRepository,
            JefeDelegacionRepository jefeDelegacionRepository,
            ParticipanteRepository participanteRepository) {
        this.equipoPreguntaRepository = equipoPreguntaRepository;
        this.equipoRepository = equipoRepository;
        this.preguntaRepository = preguntaRepository;
        this.jefeDelegacionRepository = jefeDelegacionRepository;
        this.participanteRepository = participanteRepository;
    }

    /**
     * *Asigna preguntas a todos los equipos sin asignación previa.
     */
    public void asignarPreguntasATodosLosEquipos() {
        List<Equipo> equipos = equipoRepository.findAll();
        equipos.stream()
                .filter(equipo -> equipoPreguntaRepository.findByEquipoIdEquipo(equipo.getIdEquipo()).isEmpty())
                .forEach(this::asignarPreguntasAEquipo);

        actualizarPreguntasUsadas();
    }

    /**
     * *Asigna preguntas a un equipo específico.
     *
     * @param equipo Objeto Equipo.
     */
    private void asignarPreguntasAEquipo(Equipo equipo) {
        String claseRequerida = determinarClasePorCategoria(equipo.getEquipoCategoria());
        List<Pregunta> preguntasDisponibles = obtenerPreguntasDisponibles(claseRequerida);

        if (preguntasDisponibles.size() < 5) {
            throw new IllegalArgumentException(
                    "No hay suficientes preguntas disponibles para asignar al equipo " + equipo.getNombreEquipo());
        }

        Collections.shuffle(preguntasDisponibles);
        preguntasDisponibles.stream()
                .limit(5)
                .forEach(pregunta -> {
                    EquipoPregunta equipoPregunta = new EquipoPregunta();
                    equipoPregunta.setEquipo(equipo);
                    equipoPregunta.setPregunta(pregunta);
                    equipoPreguntaRepository.save(equipoPregunta);
                });
    }

    /**
     * *Determina la clase de preguntas según la categoría del equipo.
     *
     * @param categoria Categoría del equipo.
     * @return Clase de preguntas requerida.
     */
    private String determinarClasePorCategoria(EquipoCategoria categoria) {
        switch (categoria.getNombreCategoria()) {
            case Constantes.CATEGORIA_COMPETENCIA:
                return Constantes.CLASE_A;
            case Constantes.CATEGORIA_JUNIOR:
                return Constantes.CLASE_B;
            default:
                throw new IllegalArgumentException(Constantes.ERROR_CATEGORIA_INVALIDA);
        }
    }

    /**
     * *Obtiene las preguntas disponibles de una clase específica.
     *
     * @param clase Clase de preguntas requerida.
     * @return Lista de preguntas disponibles.
     */
    private List<Pregunta> obtenerPreguntasDisponibles(String clase) {
        return preguntaRepository.findByClaseNombreClaseAndUsadaFalse(clase);
    }

    /**
     * *Obtiene los detalles de las preguntas asignadas a un equipo.
     *
     * @param idEquipo ID del equipo.
     * @return DTO con los detalles de las preguntas asignadas.
     */
    public PreguntasAsignadasDetalleDTO getPreguntasAsignadasAlEquipo(int idEquipo, Authentication authentication) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado."));

        // Solo valida autenticación si el parámetro no es null
        if (authentication != null) {
            JefeDelegacion jefe = jefeDelegacionRepository.findByUsuarioNormalizado(
                    authentication.getName())
                    .orElseThrow(() -> new IllegalArgumentException("Jefe de delegación no encontrado."));
            if (jefe.getParticipante().getPais().getIdPais() != equipo.getPais().getIdPais()) {
                throw new AccessDeniedException("No tienes acceso a este equipo.");
            }
        }

        List<EquipoPregunta> asignaciones = equipoPreguntaRepository.findByEquipoIdEquipo(idEquipo);

        List<PreguntaDetalleDTO> detalles = asignaciones.stream()
                .map(asignacion -> new PreguntaDetalleDTO(
                        asignacion.getPregunta().getIdPregunta(),
                        asignacion.getPregunta().getTexto()))
                .toList();

        return new PreguntasAsignadasDetalleDTO(equipo.getNombreEquipo(), detalles);
    }

    /**
     * *Actualiza el estado de las preguntas asignadas como "usadas".
     */
    private void actualizarPreguntasUsadas() {
        equipoPreguntaRepository.findAll().forEach(asignacion -> {
            Pregunta pregunta = asignacion.getPregunta();
            if (!pregunta.isUsada()) {
                pregunta.setUsada(true);
                preguntaRepository.save(pregunta);
            }
        });
    }

    /**
     * *Devuelve la lista de equipos con la cantidad de preguntas asignadas,
     * *filtrando por nombre, país, categoría y asignación.
     * 
     * @param nombre      Nombre del equipo (opcional)
     * @param idPais      ID del país (opcional)
     * @param idCategoria ID de la categoría (opcional)
     * @param asignado    null=Todos, true=Con preguntas, false=Sin preguntas
     * @return Lista de equipos con cantidad de preguntas asignadas
     */
    public List<EquipoConPreguntasDTO> buscarEquiposConPreguntasAsignadas(String nombre, Integer idPais,
            Integer idCategoria, Boolean asignado) {
        List<Object[]> resultados = equipoRepository.buscarEquiposConPreguntasAsignadas(nombre, idPais, idCategoria);
        List<EquipoConPreguntasDTO> lista = new java.util.ArrayList<>();
        for (Object[] fila : resultados) {
            Equipo equipo = (Equipo) fila[0];
            Long cantidad = (Long) fila[1];
            if (asignado == null || (asignado && cantidad > 0) || (!asignado && cantidad == 0)) {
                lista.add(new EquipoConPreguntasDTO(equipo, cantidad.intValue()));
            }
        }
        return lista;
    }

    /**
     * *Devuelve los contadores de equipos: total, con preguntas y sin preguntas.
     */
    public EquiposContadoresDTO obtenerContadoresEquipos() {
        List<Equipo> equipos = equipoRepository.findAll();
        int total = equipos.size();
        int conPreguntas = 0;
        for (Equipo equipo : equipos) {
            if (!equipoPreguntaRepository.findByEquipoIdEquipo(equipo.getIdEquipo()).isEmpty()) {
                conPreguntas++;
            }
        }
        int sinPreguntas = total - conPreguntas;
        return new EquiposContadoresDTO(total, conPreguntas, sinPreguntas);
    }

    /**
     * *Asigna preguntas a un equipo específico por su ID.
     * 
     * @param idEquipo ID del equipo
     */
    public void asignarPreguntasAEquipoPorId(int idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado."));
        if (!equipoPreguntaRepository.findByEquipoIdEquipo(equipo.getIdEquipo()).isEmpty()) {
            throw new IllegalArgumentException("El equipo ya tiene preguntas asignadas.");
        }
        asignarPreguntasAEquipo(equipo);
        actualizarPreguntasUsadas();
    }

    /**
     * *Obtiene las preguntas asignadas al equipo del participante.
     *
     * @param idParticipante ID del participante.
     * @return DTO con los detalles de las preguntas asignadas al equipo del
     *         participante.
     */
    @Override
    public PreguntasAsignadasDetalleDTO getPreguntasAsignadasDeParticipante(int idParticipante)
            throws IllegalArgumentException {
        var participanteOpt = participanteRepository.findById(idParticipante);
        if (participanteOpt.isEmpty()) {
            throw new IllegalArgumentException("Participante no encontrado.");
        }
        var participante = participanteOpt.get();
        if (participante.getEquipo() == null) {
            throw new IllegalArgumentException("No estás asignado a un equipo.");
        }
        int idEquipo = participante.getEquipo().getIdEquipo();

        return getPreguntasAsignadasAlEquipo(idEquipo, null);
    }
}