package com.mycompany.corporate.talent.hub.control.flujo.modelo;

/**
 * TASK 3 - Rol Gerente. Al ser hoja "final" de la jerarquía sellada, expone
 * su atributo específico (presupuestoMensual) como privado con getter.
 */
public final class Gerente extends Empleado implements Promocionable {

    private final double presupuestoMensual;

    public Gerente(int id, String nombre, byte edad, double salario, double presupuestoMensual) {
        super(id, nombre, edad, salario);
        this.presupuestoMensual = presupuestoMensual;
    }

    public double getPresupuestoMensual() {
        return presupuestoMensual;
    }

    @Override
    public double calcularBonoAscenso() {
        return (salario * 0.20) + (presupuestoMensual * 0.01);
    }
}
