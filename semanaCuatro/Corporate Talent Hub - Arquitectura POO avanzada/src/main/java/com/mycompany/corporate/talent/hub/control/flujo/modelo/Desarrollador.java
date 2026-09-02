package com.mycompany.corporate.talent.hub.control.flujo.modelo;

/**
 * TASK 3 - Rol Desarrollador. Al ser hoja "final" de la jerarquía sellada,
 * expone su atributo específico (lenguajePrincipal) como privado con getter.
 */
public final class Desarrollador extends Empleado implements Promocionable {

    private final String lenguajePrincipal;

    public Desarrollador(int id, String nombre, byte edad, double salario, String lenguajePrincipal) {
        super(id, nombre, edad, salario);
        this.lenguajePrincipal = lenguajePrincipal;
    }

    public String getLenguajePrincipal() {
        return lenguajePrincipal;
    }

    @Override
    public double calcularBonoAscenso() {
        return salario * 0.15;
    }
}
