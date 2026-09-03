package com.riwi.talent.model;

/**
 * Empleado extiende la jerarquía sellada un nivel más: "sealed permits
 * Desarrollador, Gerente" garantiza en tiempo de compilación que esos son
 * los únicos dos roles de empleado persistidos en la base de datos
 * (ver EmpleadoDAOImpl, columna tipo_perfil).
 */
public abstract sealed class Empleado extends Persona permits Desarrollador, Gerente {

    protected final byte edad;
    protected final double salario;

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
}
