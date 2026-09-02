package com.mycompany.corporate.talent.hub.control.flujo.modelo;

/**
 * TASK 1 - Empleado extiende la jerarquía sellada un nivel más: al
 * declararse también "sealed permits Desarrollador, Gerente", el
 * compilador garantiza que esos son los únicos dos roles de empleado
 * válidos en el dominio (ver modelo.legacy.EmpleadoLegacy para el
 * contraste con la herencia abierta).
 */
public abstract sealed class Empleado extends Persona permits Desarrollador, Gerente {

    protected final byte edad;
    protected final double salario;
    protected double promedioDesempeno;

    protected Empleado(int id, String nombre, byte edad, double salario) {
        super(id, nombre);
        this.edad = edad;
        this.salario = salario;
    }

    public byte getEdad() {
        return edad;
    }

    public double getSalario() {
        return salario;
    }

    public double getPromedioDesempeno() {
        return promedioDesempeno;
    }

    public void setPromedioDesempeno(double promedioDesempeno) {
        this.promedioDesempeno = promedioDesempeno;
    }
}
