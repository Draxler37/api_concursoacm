package com.concursoacm.models;

import jakarta.persistence.*;

/**
 * * Clase que representa la relación entre un equipo y una categoría en el
 * * sistema.
 * * Esta entidad se utiliza para asignar categorías a equipos específicos.
 */
@Entity
@Table(name = "equipos_categorias")
public class EquipoCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEquipoCategoria;

    @Column(nullable = false)
    private String nombreCategoria;

    // Getters y setters
    /*
     * * Método que obtiene el id de la categoría.
     */
    public int getIdEquipoCategoria() {
        return idEquipoCategoria;
    }

    public void setIdEquipoCategoria(int idEquipoCategoria) {
        this.idEquipoCategoria = idEquipoCategoria;
    }

    /*
     * * Método que obtiene el nombre de la categoría.
     */
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}