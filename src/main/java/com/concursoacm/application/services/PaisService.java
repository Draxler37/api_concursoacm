package com.concursoacm.application.services;

import com.concursoacm.application.dtos.pais.PaisDTO;
import com.concursoacm.interfaces.services.IPaisService;
import com.concursoacm.models.Pais;
import com.concursoacm.models.Region;
import com.concursoacm.tools.repositories.PaisRepository;
import com.concursoacm.tools.repositories.RegionRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * *Servicio que implementa la lógica de negocio para la gestión de países.
 */
@Service
public class PaisService implements IPaisService {

    private final PaisRepository paisRepository;
    private final RegionRepository regionRepository;

    /**
     * *Constructor que inyecta las dependencias necesarias.
     *
     * @param paisRepository   Repositorio para la gestión de países.
     * @param regionRepository Repositorio para la gestión de regiones.
     */
    public PaisService(PaisRepository paisRepository, RegionRepository regionRepository) {
        this.paisRepository = paisRepository;
        this.regionRepository = regionRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PaisDTO> obtenerTodosLosPaises() {
        return paisRepository.findAll().stream()
                .map(PaisDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PaisDTO> obtenerPaisPorId(int id) {
        return Optional.of(new PaisDTO(buscarPaisPorId(id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaisDTO guardarPais(Pais pais) {
        Region region = buscarRegionPorId(pais.getRegion().getIdRegion());
        pais.setRegion(region);
        return new PaisDTO(paisRepository.save(pais));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaisDTO actualizarPais(int id, Pais nuevoPais) {
        Pais paisExistente = buscarPaisPorId(id);
        paisExistente.setNombrePais(nuevoPais.getNombrePais());
        paisExistente.setCodigoTelefonico(nuevoPais.getCodigoTelefonico());
        Region region = buscarRegionPorId(nuevoPais.getRegion().getIdRegion());
        paisExistente.setRegion(region);
        return new PaisDTO(paisRepository.save(paisExistente));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String eliminarPais(int id) {
        paisRepository.delete(buscarPaisPorId(id));
        return "Eliminado con éxito.";
    }

    /**
     * *Método privado para buscar un país por su ID.
     *
     * @param id ID del país.
     * @return Objeto Pais si se encuentra.
     * @throws RuntimeException si no se encuentra el país.
     */
    private Pais buscarPaisPorId(int id) {
        return paisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El país con ID " + id + " no existe."));
    }

    /**
     * *Método privado para buscar una región por su ID.
     *
     * @param idRegion ID de la región.
     * @return Objeto Region si se encuentra.
     * @throws RuntimeException si no se encuentra la región.
     */
    private Region buscarRegionPorId(int idRegion) {
        return regionRepository.findById(idRegion)
                .orElseThrow(() -> new RuntimeException("La región con ID " + idRegion + " no existe."));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PaisDTO> obtenerPaisesPorRegion(int regionId) {
        return paisRepository.findByRegionIdRegion(regionId)
                .stream().map(PaisDTO::new).collect(Collectors.toList());
    }
}
