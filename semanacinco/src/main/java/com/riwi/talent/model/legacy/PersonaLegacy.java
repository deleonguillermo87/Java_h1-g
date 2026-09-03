package com.riwi.talent.model.legacy;

/**
 * Estilo Legacy (Java 8/11): herencia abierta. Cualquier clase puede
 * extender esta clase sin pedir permiso al diseñador original -justo lo
 * que las Sealed Classes (ver modelo.Persona) vienen a restringir.
 * Clase de referencia/comparación: no participa en el flujo real de la
 * aplicación (model/controller/view).
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
