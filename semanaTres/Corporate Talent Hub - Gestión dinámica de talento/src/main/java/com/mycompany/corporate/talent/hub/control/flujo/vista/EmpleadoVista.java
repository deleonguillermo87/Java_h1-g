/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.corporate.talent.hub.control.flujo.vista;

import com.mycompany.corporate.talent.hub.control.flujo.modelo.Empleado;
import com.mycompany.corporate.talent.hub.control.flujo.servicio.EmpleadoServicio;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

public class EmpleadoVista {
    private final EmpleadoServicio servicio = new EmpleadoServicio();
    private static final double NOTA_MINIMA = 0.0;
    private static final double NOTA_MAXIMA = 100.0;
    private static final double PROMEDIO_PARA_PROMOCION = 80.0;

    public void iniciar() {
        try (var scanner = new Scanner(System.in)) {
            var sistemaActivo = true;
            do {
                mostrarMenu();
                try {
                    System.out.print("Seleccione una opción: ");
                    var opcion = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcion) {
                        case 1 -> registrar(scanner);
                        case 2 -> mostrarReporte();
                        case 3 -> mostrarCategoriasSalariales();
                        case 4 -> eliminarBajoPromedio(scanner);
                        case 5 -> System.out.println(servicio.generarReporteFinal());
                        case 0 -> {
                            sistemaActivo = false;
                            System.out.println("Sesión finalizada.");
                        }
                        default -> System.out.println("Opción fuera del menú.");
                    }
                } catch (InputMismatchException excepcion) {
                    System.out.println("Entrada inválida. Debe escribir un valor numérico.");
                    scanner.nextLine();
                }
            } while (sistemaActivo);
        }
    }

    private void mostrarMenu() {
        System.out.println("""
                =====================================
                     CORPORATE TALENT HUB (CAPAS)
                =====================================
                1. Registrar empleado y calificaciones
                2. Mostrar reporte de desempeño
                3. Consultar categorías salariales
                4. Eliminar empleados bajo promedio mínimo
                5. Reporte final (total y promedio de salarios)
                0. Salir
                """);
    }

    private void registrar(Scanner scanner) {
        if (servicio.getCantidadEmpleados() >= servicio.getCantidadEmpleados()) {
            System.out.println("No hay espacio para más empleados.");
            return;
        }

        System.out.print("ID positivo: ");
        var id = scanner.nextInt();
        scanner.nextLine();

        if (id <= 0) {
            System.out.println("El ID debe ser mayor que cero.");
            return;
        } 
        if (servicio.idRepetido(id)) {
            System.out.println("Ya existe un empleado con ese ID.");
            return;
        }

        System.out.print("Nombre: ");
        var nombre = scanner.nextLine().trim();
        if (nombre.isBlank()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        System.out.print("Edad entre 18 y 100: ");
        var edadIngresada = scanner.nextInt();
        if (edadIngresada < 18 || edadIngresada > 100) {
            System.out.println("La edad está fuera del rango permitido.");
            return;
        }

        System.out.print("Salario mayor que cero: ");
        var salario = scanner.nextDouble();
        if (salario <= 0) {
            System.out.println("El salario debe ser mayor que cero.");
            return;
        }

        double[] notas = new double[servicio.getCantidadTrimestres()];
        for (var t = 0; t < servicio.getCantidadTrimestres(); t++) {
            System.out.printf("Calificación del trimestre %d (0 a 100): ", t + 1);
            var calificacion = scanner.nextDouble();

            if (calificacion < NOTA_MINIMA || calificacion > NOTA_MAXIMA) {
                System.out.println("La calificación está fuera del rango permitido.");
                return;
            }
            notas[t] = calificacion;
        }
        scanner.nextLine();

        Empleado nuevo = new Empleado(id, nombre, (byte) edadIngresada, salario);
        servicio.guardarEmpleado(nuevo, notas);
        System.out.println("Empleado registrado correctamente.");
    }

    private void mostrarReporte() {
        int total = servicio.getCantidadEmpleados();
        if (total == 0) {
            System.out.println("Todavía no hay empleados registrados.");
            return;
        }

        System.out.println("\nREPORTE DE DESEMPEÑO");
        List<Empleado> lista = servicio.getEmpleados();

        for (Empleado empleado : lista) {
            var promedio = servicio.calcularPromedio(empleado.getId());
            empleado.setPromedioDesempeno(promedio);

            int promedioEntero = (int) promedio;
            System.out.printf("ID: %d | Nombre: %s | Promedio: %d%% ", 
                    empleado.getId(), empleado.getNombre(), promedioEntero);
            
            if (promedio >= PROMEDIO_PARA_PROMOCION) {
                System.out.println("[APLICA PARA PROMOCIÓN]");
            } else {
                System.out.println("[SIN CAMBIOS]");
            }
        }
    }

    private void mostrarCategoriasSalariales() {
        System.out.println("\n--- CATEGORÍAS SALARIALES ---");
        System.out.println("Junior:   Hasta $1,500");
        System.out.println("Mid-Level: De $1,501 a $3,000");
        System.out.println("Senior:   Más de $3,000");
    }

    private void eliminarBajoPromedio(Scanner scanner) {
        if (servicio.getCantidadEmpleados() == 0) {
            System.out.println("Todavía no hay empleados registrados.");
            return;
        }

        System.out.print("Promedio mínimo requerido (0 a 100): ");
        var puntajeMinimo = scanner.nextDouble();
        scanner.nextLine();

        if (puntajeMinimo < NOTA_MINIMA || puntajeMinimo > NOTA_MAXIMA) {
            System.out.println("El promedio mínimo está fuera del rango permitido.");
            return;
        }

        var totalAntes = servicio.getCantidadEmpleados();
        servicio.eliminarEmpleadosBajoPromedio(puntajeMinimo);
        var totalDespues = servicio.getCantidadEmpleados();

        System.out.printf("Se eliminaron %d empleado(s) con promedio menor a %.1f%n",
                (totalAntes - totalDespues), puntajeMinimo);
    }
}