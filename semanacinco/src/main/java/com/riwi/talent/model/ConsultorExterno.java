package com.riwi.talent.model;

/**
 * Segunda rama permitida por la sealed class Persona. Modela un perfil que
 * no es empleado de planta (no pasa por EmpleadoDAO), pero que el
 * compilador reconoce como parte cerrada del dominio Persona. Al ser
 * "final", tampoco puede extenderse más allá.
 */
public final class ConsultorExterno extends Persona {

    private final double tarifaHora;

    public ConsultorExterno(int id, String nombre, double tarifaHora) {
        super(id, nombre);
        this.tarifaHora = tarifaHora;
    }

    public double getTarifaHora() {
        return tarifaHora;
    }
}
