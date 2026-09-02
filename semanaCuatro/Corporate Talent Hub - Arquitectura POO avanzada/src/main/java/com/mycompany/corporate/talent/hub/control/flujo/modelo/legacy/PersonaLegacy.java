package com.mycompany.corporate.talent.hub.control.flujo.modelo.legacy;

/**
 * TASK 1 - Estilo Legacy (Java 8/11): herencia abierta.
 *
 * ANÁLISIS:
 * Cualquier clase, en cualquier paquete o módulo, puede extender esta clase
 * sin pedir permiso al diseñador original. El compilador no conoce el
 * conjunto completo de subtipos posibles, así que no se puede hacer un
 * switch exhaustivo sobre sus subclases ni impedir que otro equipo cree
 * una subclase inválida para el dominio. Esta apertura es justamente lo
 * que las Sealed Classes (ver modelo.Persona) vienen a restringir.
 *
 * Clase de referencia/comparación: no participa en el flujo real de la
 * aplicación (modelo.servicio.vista), solo documenta el contraste.
 */
public abstract class PersonaLegacy {

    protected int id;
    protected String nombre;

    protected PersonaLegacy(int id, String nombre) {
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
