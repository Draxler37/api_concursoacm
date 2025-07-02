package com.concursoacm.utils;

/**
 * *Clase que contiene constantes utilizadas en todo el proyecto.
 */
public final class Constantes {
    private static final Constantes INSTANCE = new Constantes();

    private Constantes() {
        // Constructor privado
    }

    public static Constantes getInstance() {
        return INSTANCE;
    }

    // Roles
    public static final String ROL_JEFE_DELEGACION = "JEFE_DELEGACION";
    public static final String ROL_PARTICIPANTES = "PARTICIPANTES";

    // Categorías de equipos
    public static final String CATEGORIA_COMPETENCIA = "Competencia";
    public static final String CATEGORIA_JUNIOR = "Junior";

    // Clases de preguntas
    public static final String CLASE_A = "A";
    public static final String CLASE_B = "B";

    // Mensajes de error
    public static final String ERROR_USUARIO_NO_ENCONTRADO = "Usuario no encontrado";
    public static final String ERROR_EQUIPO_NO_ENCONTRADO = "Equipo no encontrado.";
    public static final String ERROR_PREGUNTA_NO_ENCONTRADA = "Pregunta no encontrada.";
    public static final String ERROR_NO_AUTORIZADO = "No estás autorizado para realizar esta acción.";
    public static final String ERROR_CATEGORIA_INVALIDA = "Categoría de equipo no válida.";

    // Otros
    public static final int PREGUNTAS_MINIMAS = 25;
}