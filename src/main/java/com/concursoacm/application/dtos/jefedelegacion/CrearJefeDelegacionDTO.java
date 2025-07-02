package com.concursoacm.application.dtos.jefedelegacion;

import com.concursoacm.application.dtos.pais.PaisDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CrearJefeDelegacionDTO {

    @NotBlank(message = "El nombre no puede estar vacío.")
    private String nombre;

    @NotBlank(message = "El número de carnet no puede estar vacío.")
    private String numCarnet;

    @NotNull(message = "La edad no puede estar vacía.")
    private Integer edad;

    @NotBlank(message = "El sexo no puede estar vacío.")
    private String sexo;

    @NotNull(message = "El país no puede estar vacío.")
    private PaisDTO pais;

    // Datos del jefe de delegación
    @NotBlank(message = "El usuario no puede estar vacío.")
    private String usuario;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    private String contraseña;

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumCarnet() {
        return numCarnet;
    }

    public void setNumCarnet(String numCarnet) {
        this.numCarnet = numCarnet;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public PaisDTO getPais() {
        return pais;
    }

    public void setPais(PaisDTO pais) {
        this.pais = pais;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
