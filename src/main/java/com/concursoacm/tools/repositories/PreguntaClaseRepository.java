package com.concursoacm.tools.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concursoacm.models.PreguntaClase;

/*
 * * Repositorio para la gestión de preguntas de clase en la base de datos.
 */
@Repository
public interface PreguntaClaseRepository extends JpaRepository<PreguntaClase, Integer> {

}
