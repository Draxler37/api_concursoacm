package com.concursoacm.tools.repositories;

import com.concursoacm.models.EquipoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * * Repositorio para la gestión de categorías de equipos en la base de datos.
 * * Extiende JpaRepository para proporcionar operaciones CRUD y consultas
 * * personalizadas.
 */
@Repository
public interface EquipoCategoriaRepository extends JpaRepository<EquipoCategoria, Integer> {
}