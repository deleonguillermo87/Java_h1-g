/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.modelo;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Coder
 */
public class Catalogos {
    private Catalogos() {
        // evita que se instancie, es una clase de solo constantes
    }

    public static final List<String> TECNOLOGIAS = List.of(
        "Java", "JavaScript", "Python", "SQL", "React"
    );

    public static final Map<String, String> SEDES = Map.of(
        "BAQ", "Barranquilla",
        "BOG", "Bogotá",
        "MED", "Medellín"
    );

    /*
     * List.of() y Map.of() (Java 9/11) son más seguros que ArrayList/HashMap
     * porque son inmutables: cualquier intento de .add()/.put() lanza
     * UnsupportedOperationException en tiempo de ejecución, no permiten
     * elementos null (fallan al crearse, detectando errores temprano), y
     * son thread-safe al no poder modificarse por varios hilos a la vez.
     *
     * Limitación: precisamente por ser inmutables, no sirven para colecciones
     * que deban crecer o cambiar en tiempo de ejecución (por eso "empleados"
     * en EmpleadoServicio sigue usando ArrayList, ya que se agregan y
     * eliminan elementos constantemente).
     */
}
