package com.mycompany.corporate.talent.hub.control.flujo.modelo;

/**
 * TASK 1 - Segunda rama permitida por la sealed class Persona. Modela un
 * perfil que NO es empleado de planta (no pasa por EmpleadoServicio), pero
 * que el compilador reconoce como parte cerrada del dominio Persona. Al ser
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
