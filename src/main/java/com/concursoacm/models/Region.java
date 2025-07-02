package com.concursoacm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * *Entidad que representa una región en el sistema.
 */
@Entity
@Table(name = "regiones")
@JsonIgnoreProperties("paises") // Evita referencia circular
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRegion;

    @Column(name = "nombre_region", nullable = false)
    @NotBlank(message = "El nombre de la región no puede estar vacío.")
    private String nombreRegion;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<Pais> paises;

    /**
     * *Constructor vacío requerido por JPA.
     */
    public Region() {
    }

    /**
     * *Constructor para inicializar una región con un ID.
     *
     * @param idRegion ID de la región.
     */
    public Region(int idRegion) {
        this.idRegion = idRegion;
    }

    // Getters y Setters

    /**
     * *Obtiene el ID de la región.
     *
     * @return ID de la región.
     */
    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    /**
     * *Obtiene el nombre de la región.
     *
     * @return Nombre de la región.
     */
    public String getNombreRegion() {
        return nombreRegion;
    }

    public void setNombreRegion(String nombreRegion) {
        this.nombreRegion = nombreRegion;
    }

    /**
     * *Obtiene la lista de países asociados a la región.
     *
     * @return Lista de países.
     */
    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }
}
