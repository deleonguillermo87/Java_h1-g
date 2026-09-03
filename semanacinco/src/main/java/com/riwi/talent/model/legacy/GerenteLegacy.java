package com.riwi.talent.model.legacy;

/**
 * Subclase de rol usada en el ejemplo legacy de instanceof + casting
 * manual (ver ValidadorLegacy).
 */
public class GerenteLegacy extends EmpleadoLegacy {

    private double presupuestoMensual;

    public GerenteLegacy(int id, String nombre, double salario, double presupuestoMensual) {
        super(id, nombre, salario);
        this.presupuestoMensual = presupuestoMensual;
    }

    public double getPresupuestoMensual() {
        return presupuestoMensual;
    }
}
