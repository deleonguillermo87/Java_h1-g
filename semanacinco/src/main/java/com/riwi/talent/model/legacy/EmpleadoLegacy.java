package com.riwi.talent.model.legacy;

/**
 * Cualquiera puede heredar de PersonaLegacy de forma abierta, sin ninguna
 * palabra clave que restrinja quién puede hacerlo.
 */
public class EmpleadoLegacy extends PersonaLegacy {

    protected double salario;

    public EmpleadoLegacy(int id, String nombre, double salario) {
        super(id, nombre);
        this.salario = salario;
    }

    public double getSalario() {
        return salario;
    }
}
