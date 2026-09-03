package com.riwi.talent.model;

/**
 * Sealed Class (Java 17/21): "permits Empleado, ConsultorExterno" declara
 * de forma explícita y cerrada cuáles son las únicas subclases autorizadas
 * de Persona. El compilador conoce el árbol completo de tipos, lo que
 * permite un switch/pattern matching exhaustivo (ver
 * EmpleadoDAOImpl.mapearEmpleado) y evita que otro paquete inyecte una
 * subclase no controlada del dominio (comparar con
 * modelo.legacy.PersonaLegacy, de herencia abierta).
 */
public abstract sealed class Persona permits Empleado, ConsultorExterno {

    protected final int id;
    protected final String nombre;

    protected Persona(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
