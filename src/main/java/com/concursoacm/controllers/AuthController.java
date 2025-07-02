package com.concursoacm.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(Authentication authentication) {
        // Verificar si la autenticación fue exitosa
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        // Obtener el nombre de usuario del contexto de seguridad
        String username = authentication.getName();

        // Retornar una respuesta exitosa con el nombre de usuario
        return ResponseEntity.ok("Usuario autenticado: " + username);
    }
}