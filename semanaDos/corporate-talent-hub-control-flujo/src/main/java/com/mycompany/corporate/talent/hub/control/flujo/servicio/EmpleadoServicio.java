/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.corporate.talent.hub.control.flujo.servicio;

import com.mycompany.corporate.talent.hub.control.flujo.modelo.Empleado;

public class EmpleadoServicio {
    private static final int MAXIMO_EMPLEADOS = 50;
    private static final int CANTIDAD_TRIMESTRES = 3;
    
    private final Empleado[] empleados = new Empleado[MAXIMO_EMPLEADOS];
    private final double[][] calificaciones = new double[MAXIMO_EMPLEADOS][CANTIDAD_TRIMESTRES];
    private int cantidadEmpleados = 0;

    public int getCantidadEmpleados() {
        return cantidadEmpleados;
    }

    public int getMaximoEmpleados() {
        return MAXIMO_EMPLEADOS;
    }

    public int getCantidadTrimestres() {
        return CANTIDAD_TRIMESTRES;
    }

    public boolean idRepetido(int idBuscado) {
        for (var indice = 0; indice < cantidadEmpleados; indice++) {
            if (empleados[indice].getId() == idBuscado) {
                return true;
            }
        }
        return false;
    }

    public void guardarEmpleado(Empleado empleado, double[] notas) {
        empleados[cantidadEmpleados] = empleado;
        for (int t = 0; t < CANTIDAD_TRIMESTRES; t++) {
            calificaciones[cantidadEmpleados][t] = notas[t];
        }
        cantidadEmpleados++;
    }

    public Empleado[] getEmpleados() {
        return empleados;
    }

    public double calcularPromedio(int fila) {
        var suma = 0.0;
        for (var columna = 0; columna < CANTIDAD_TRIMESTRES; columna++) {
            suma += calificaciones[fila][columna];
        }
        return suma / CANTIDAD_TRIMESTRES;
    }
}

