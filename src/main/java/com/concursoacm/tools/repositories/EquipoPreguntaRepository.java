package com.concursoacm.tools.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concursoacm.models.EquipoPregunta;

import java.util.List;

/**
 * *Repositorio para la gestión de la entidad EquipoPregunta.
 * *Proporciona métodos para realizar operaciones CRUD y consultas
 * *personalizadas.
 */
@Repository
public interface EquipoPreguntaRepository extends JpaRepository<EquipoPregunta, Integer> {

    /**
     * *Obtiene la lista de preguntas asociadas a un equipo específico.
     *
     * @param idEquipo ID del equipo.
     * @return Lista de preguntas asociadas al equipo.
     */
    List<EquipoPregunta> findByEquipoIdEquipo(int idEquipo);
}