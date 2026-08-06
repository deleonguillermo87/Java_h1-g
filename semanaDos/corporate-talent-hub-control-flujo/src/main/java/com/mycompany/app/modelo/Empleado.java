/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.modelo;

/**
 * TASK 2: Clase tradicional compatible con Java 8.
 * 
 * ANÁLISIS:
 * - Verbosidad vs Brevedad: Esta clase requiere decenas de líneas manuales (campos, constructor, getters/setters)
 *   frente a la estructura compacta de una línea que ofrece un Record.
 * - Inmutabilidad: Esta clase maneja estado mutable mediante métodos setters, permitiendo modificar
 *   sus valores tras la creación del objeto.
 */
public class Empleado {

    // Los 8 tipos primitivos requeridos:
    private byte nivelAcceso;
    private short anioIngreso;
    private int idEmpleado;
    private long numeroDocumento; // Requiere sufijo L
    private float puntajeTest;    // Requiere sufijo f
    private double salarioBase;
    private char tipoContrato;
    private boolean esActivo;

    // String no es primitivo: es una clase que guarda una referencia.
    private String nombre;

    // Variables de apoyo para las reglas de negocio (TASK 3)
    private int edad;
    private int idSede;
    private double bonoMensual;

    // Constructor Completo
    public Empleado(
            byte nivelAcceso,
            short anioIngreso,
            int idEmpleado,
            long numeroDocumento,
            float puntajeTest,
            double salarioBase,
            char tipoContrato,
            boolean esActivo,
            String nombre,
            int edad,
            int idSede,
            double bonoMensual) {
        this.nivelAcceso = nivelAcceso;
        this.anioIngreso = anioIngreso;
        this.idEmpleado = idEmpleado;
        this.numeroDocumento = numeroDocumento;
        this.puntajeTest = puntajeTest;
        this.salarioBase = salarioBase;
        this.tipoContrato = tipoContrato;
        this.esActivo = esActivo;
        this.nombre = nombre;
        this.edad = edad;
        this.idSede = idSede;
        this.bonoMensual = bonoMensual;
    }

    /**
     * TASK 3: Lógica aritmética.
     * Orden de ejecución: Paréntesis -> Multiplicación -> Suma/Resta.
     */
    public double calcularSalarioFinal() {
        return (salarioBase + (bonoMensual * 1.10)) - (salarioBase * 0.05);
    }

    /**
     * TASK 3: Uso del módulo (%)
     */
    public boolean tieneBonoExtra() {
        return idEmpleado % 2 == 0;
    }

    /**
     * TASK 3: Lógica booleana compleja.
     * Precedencia de evaluación: Primero !, después && y finalmente ||.
     */
    public boolean validarElegibilidad() {
        return (puntajeTest > 85 && edad < 30) || (idSede == 1 && !esActivo);
    }

    /**
     * TASK 3: Asignación compuesta (+=)
     */
    public void actualizarBonoMensual(double incremento) {
        bonoMensual += incremento;
    }

    // Getters y Setters necesarios para diagnóstico
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getIdEmpleado() { return idEmpleado; }
    public double getSalarioBase() { return salarioBase; }
    public double getBonoMensual() { return bonoMensual; }

    @Override
    public String toString() {
        return "Empleado{" +
                "\nnivelAcceso=" + nivelAcceso +
                " \n , añioIngreso=" + anioIngreso +
                "\n , idEmpleado=" + idEmpleado +
                "\n , numeroDocumento=" + numeroDocumento +
                "\n , puntajeTest=" + puntajeTest +
                "\n , salarioBase=" + salarioBase +
                "\n , tipoContrato=" + tipoContrato +
                "\n , esActivo=" + esActivo +
                "\n , nombre='" + nombre + '\'' +
                "\n , edad=" + edad +
                "\n , idSede=" + idSede +
                " \n, bonoMensual=" + bonoMensual +
                '}';
    }
}
