package com.mycompany.corporate.talent.hub.control.flujo.modelo;

/**
 * TASK 1 - Estilo Moderno (Java 17/21): Sealed Class.
 *
 * ANÁLISIS:
 * "permits Empleado, ConsultorExterno" declara de forma explícita y cerrada
 * cuáles son las únicas subclases autorizadas de Persona. El compilador
 * conoce el árbol completo de tipos, lo que permite un switch/pattern
 * matching exhaustivo sobre Persona (sin rama "default") y evita que
 * cualquier otro paquete inyecte una subclase no controlada del dominio.
 * Frente a la herencia abierta (ver modelo.legacy.PersonaLegacy), esto
 * ofrece más seguridad en el diseño de APIs porque el contrato de la
 * jerarquía queda garantizado por el propio lenguaje, no solo por
 * convención o documentación.
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
