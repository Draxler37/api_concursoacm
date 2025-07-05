package com.concursoacm.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.concursoacm.interfaces.services.IParticipanteService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IParticipanteService participanteService;

    public AuthController(IParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

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

    @GetMapping("/validar")
    public ResponseEntity<Integer> validarParticipante(@RequestParam String nombre) {
        // Busca solo participantes que NO sean jefes de delegación
        Integer idParticipante = participanteService.obtenerIdPorNombre(nombre);

        if (idParticipante != null) {
            return ResponseEntity.ok(idParticipante);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}