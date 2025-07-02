package com.concursoacm.application.services;

import com.concursoacm.interfaces.services.IRegionService;
import com.concursoacm.models.Region;
import com.concursoacm.tools.repositories.RegionRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * *Servicio que implementa la lógica de negocio para la gestión de regiones.
 */
@Service
public class RegionService implements IRegionService {

    private final RegionRepository regionRepository;

    /**
     * *Constructor que inyecta el repositorio de regiones.
     *
     * @param regionRepository Repositorio para la gestión de regiones.
     */
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Region> obtenerTodasLasRegiones() {
        return regionRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Region> obtenerRegionPorId(int id) {
        if (!regionRepository.existsById(id)) {
            throw new IllegalArgumentException("La región con el ID " + id + " no existe.");
        }
        return regionRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Region guardarRegion(Region region) {
        return regionRepository.save(region);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Region actualizarRegion(int id, Region nuevaRegion) {
        Region regionExistente = buscarRegionPorId(id); // Reutilizamos el método privado
        regionExistente.setNombreRegion(nuevaRegion.getNombreRegion());
        return regionRepository.save(regionExistente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eliminarRegion(int id) {
        Region regionExistente = buscarRegionPorId(id); // Reutilizamos el método privado
        regionRepository.delete(regionExistente);
    }

    /**
     * *Método privado para buscar una región por su ID.
     *
     * @param id ID de la región.
     * @return Objeto Region si se encuentra.
     * @throws IllegalArgumentException si no se encuentra la región.
     */
    private Region buscarRegionPorId(int id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La región con ID " + id + " no existe."));
    }
}
