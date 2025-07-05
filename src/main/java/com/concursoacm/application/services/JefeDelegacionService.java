package com.concursoacm.application.services;

import com.concursoacm.application.dtos.jefedelegacion.CrearJefeDelegacionDTO;
import com.concursoacm.application.dtos.jefedelegacion.JefeDelegacionDTO;
import com.concursoacm.application.dtos.jefedelegacion.JefeDelegacionFiltroDTO;
import com.concursoacm.interfaces.services.IJefeDelegacionService;
import com.concursoacm.models.JefeDelegacion;
import com.concursoacm.models.Pais;
import com.concursoacm.models.Participante;
import com.concursoacm.tools.repositories.JefeDelegacionRepository;
import com.concursoacm.tools.repositories.PaisRepository;
import com.concursoacm.tools.repositories.ParticipanteRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * *Servicio que implementa la lógica de negocio para la gestión de jefes de
 * *delegación.
 */
@Service
public class JefeDelegacionService implements IJefeDelegacionService {

    private final PasswordEncoder passwordEncoder;

    private final JefeDelegacionRepository jefeDelegacionRepository;
    private final ParticipanteRepository participanteRepository;
    private final PaisRepository paisRepository;

    /**
     * *Constructor que inyecta las dependencias necesarias.
     *
     * @param jefeDelegacionRepository Repositorio para la gestión de jefes de
     *                                 delegación.
     * @param participanteRepository   Repositorio para la gestión de participantes.
     */
    public JefeDelegacionService(JefeDelegacionRepository jefeDelegacionRepository,
            ParticipanteRepository participanteRepository, PaisRepository paisRepository,
            PasswordEncoder passwordEncoder) {
        this.jefeDelegacionRepository = jefeDelegacionRepository;
        this.participanteRepository = participanteRepository;
        this.passwordEncoder = passwordEncoder;
        this.paisRepository = paisRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JefeDelegacionDTO> obtenerTodosLosJefes() {
        return jefeDelegacionRepository.findAll().stream()
                .map(jefe -> new JefeDelegacionDTO(
                        jefe.getIdJefe(),
                        jefe.getParticipante().getNombre()))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<JefeDelegacionDTO> obtenerJefePorId(int idJefe) {
        JefeDelegacion jefe = buscarJefePorId(idJefe); // Reutilizamos el método privado
        return Optional.of(new JefeDelegacionDTO(jefe.getIdJefe(), jefe.getParticipante().getNombre()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JefeDelegacion crearJefeDelegacion(CrearJefeDelegacionDTO request) {
        // Validar que el país exista
        Pais pais = paisRepository.findById(request.getPais().getIdPais())
                .orElseThrow(() -> new RuntimeException("País no encontrado"));

        // Validar que no exista ya un jefe de delegación para el país
        if (jefeDelegacionRepository.countByPaisId(pais.getIdPais()) >= 1) {
            throw new IllegalArgumentException("Ya existe un jefe de delegación para este país.");
        }

        // Validar edad mínima
        if (request.getEdad() < 18) {
            throw new IllegalArgumentException("El participante debe ser mayor de edad para ser jefe de delegación.");
        }

        // Crear el participante
        Participante nuevoParticipante = new Participante();
        nuevoParticipante.setNombre(request.getNombre());
        nuevoParticipante.setNumCarnet(request.getNumCarnet());
        nuevoParticipante.setEdad(request.getEdad());
        nuevoParticipante.setSexo(request.getSexo());
        nuevoParticipante.setPais(pais);
        participanteRepository.save(nuevoParticipante);

        // Crear el jefe de delegación
        JefeDelegacion nuevoJefe = new JefeDelegacion();
        nuevoJefe.setUsuarioNormalizado(normalizarTexto(request.getUsuario()));
        nuevoJefe.setContrasena(passwordEncoder.encode(request.getContraseña()));
        nuevoJefe.setParticipante(nuevoParticipante);
        return jefeDelegacionRepository.save(nuevoJefe);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean cambiarContraseña(int idJefe, String usuario, String contraseñaActual,
            String nuevaContraseña) {
        JefeDelegacion jefeDelegacion = jefeDelegacionRepository.findById(idJefe)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El jefe de delegación con ID " + idJefe + " no existe."));

        validarCambioDeContraseña(jefeDelegacion, usuario, contraseñaActual, nuevaContraseña);

        jefeDelegacion.setContrasena(passwordEncoder.encode(nuevaContraseña));
        jefeDelegacionRepository.save(jefeDelegacion);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eliminarJefeDelegacion(int idJefe) {
        JefeDelegacion jefe = buscarJefePorId(idJefe); // Reutilizamos el método privado
        jefeDelegacionRepository.delete(jefe);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JefeDelegacionFiltroDTO> obtenerJefesConPaisYRegion() {
        return jefeDelegacionRepository.findAll().stream()
                .map(jefe -> {
                    Participante p = jefe.getParticipante();
                    Pais pais = p.getPais();
                    return new JefeDelegacionFiltroDTO(
                            jefe.getIdJefe(),
                            p.getNombre(),
                            pais != null ? pais.getIdPais() : null,
                            pais != null ? pais.getNombrePais() : null,
                            pais != null && pais.getRegion() != null ? pais.getRegion().getIdRegion() : null,
                            pais != null && pais.getRegion() != null ? pais.getRegion().getNombreRegion() : null,
                            p.getEdad(),
                            p.getSexo());
                })
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JefeDelegacionFiltroDTO> buscarJefesDelegacion(String nombre, Integer idPais, Integer idRegion) {
        // Trae todos los jefes con país y región
        List<JefeDelegacionFiltroDTO> base = obtenerJefesConPaisYRegion();
        return base.stream()
                .filter(j -> {
                    boolean match = true;
                    if (nombre != null && !nombre.isBlank()) {
                        match &= j.getNombreParticipante() != null
                                && j.getNombreParticipante().toLowerCase().contains(nombre.toLowerCase());
                    }
                    if (idPais != null) {
                        match &= j.getIdPais() != null && j.getIdPais().equals(idPais);
                    }
                    if (idRegion != null) {
                        match &= j.getIdRegion() != null && j.getIdRegion().equals(idRegion);
                    }
                    return match;
                })
                .collect(Collectors.toList());
    }

    /**
     * *Valida las condiciones necesarias para cambiar la contraseña de un jefe de
     * *delegación.
     *
     * @param jefe             Objeto JefeDelegacion.
     * @param usuario          Usuario.
     * @param contraseñaActual Contraseña actual ingresada.
     * @param nuevaContraseña  Nueva contraseña ingresada.
     */
    private void validarCambioDeContraseña(JefeDelegacion jefe, String usuario, String contraseñaActual,
            String nuevaContraseña) {
        if (contraseñaActual == null || contraseñaActual.isBlank() || nuevaContraseña == null
                || nuevaContraseña.isBlank()) {
            throw new IllegalArgumentException("La 'contraseñaActual' y la 'nuevaContraseña' son obligatorias.");
        }

        if (!jefe.getUsuarioNormalizado().equals(usuario)) {
            throw new SecurityException("No puedes cambiar la contraseña de otro usuario.");
        }

        validarLongitudContraseña(nuevaContraseña);

        if (!passwordEncoder.matches(contraseñaActual, jefe.getContrasena())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta.");
        }
    }

    /**
     * *Valida que la contraseña cumpla con la longitud mínima requerida.
     *
     * @param contraseña Contraseña a validar.
     */
    private void validarLongitudContraseña(String contraseña) {
        if (contraseña.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }
    }

    /**
     * *Normaliza un texto eliminando acentos y convirtiéndolo a minúsculas.
     *
     * @param texto Texto a normalizar.
     * @return Texto normalizado.
     */
    private String normalizarTexto(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase();
    }

    /**
     * *Método privado para buscar un jefe de delegación por su ID.
     *
     * @param idJefe ID del jefe de delegación.
     * @return Objeto JefeDelegacion si se encuentra.
     * @throws IllegalArgumentException si no se encuentra el jefe de delegación.
     */
    private JefeDelegacion buscarJefePorId(int idJefe) {
        return jefeDelegacionRepository.findById(idJefe)
                .orElseThrow(
                        () -> new IllegalArgumentException("El jefe de delegación con ID " + idJefe + " no existe."));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer obtenerIdPaisPorNombre(String nombre) {
        return jefeDelegacionRepository.obtenerIdPaisPorNombreUsuario(nombre)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Jefe de delegación no encontrado con el nombre: " + nombre));
    }
}
