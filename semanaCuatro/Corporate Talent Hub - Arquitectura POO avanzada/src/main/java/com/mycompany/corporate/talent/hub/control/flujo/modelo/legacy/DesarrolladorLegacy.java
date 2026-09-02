package com.mycompany.corporate.talent.hub.control.flujo.modelo.legacy;

/**
 * TASK 3 - Subclase de rol usada en el ejemplo legacy de instanceof +
 * casting manual (ver ValidadorLegacy).
 */
public class DesarrolladorLegacy extends EmpleadoLegacy {

    private String lenguajePrincipal;

    public DesarrolladorLegacy(int id, String nombre, double salario, String lenguajePrincipal) {
        super(id, nombre, salario);
        this.lenguajePrincipal = lenguajePrincipal;
    }

    public String getLenguajePrincipal() {
        return lenguajePrincipal;
    }
}
