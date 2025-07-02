package com.concursoacm.application.dtos.jefedelegacion;

import jakarta.validation.constraints.NotBlank;

public class CambiarContrasenaDTO {

    @NotBlank(message = "La contraseña actual no puede estar vacía.")
    private String contrasenaActual;

    @NotBlank(message = "La nueva contraseña no puede estar vacía.")
    private String nuevaContrasena;

    // Getters y setters
    public String getContrasenaActual() {
        return contrasenaActual;
    }

    public void setContraseñaActual(String contrasenaActual) {
        this.contrasenaActual = contrasenaActual;
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }
}
