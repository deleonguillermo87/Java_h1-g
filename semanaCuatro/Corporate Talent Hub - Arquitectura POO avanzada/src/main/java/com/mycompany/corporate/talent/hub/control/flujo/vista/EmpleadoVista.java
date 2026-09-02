/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.corporate.talent.hub.control.flujo.vista;

import com.mycompany.corporate.talent.hub.control.flujo.modelo.DesempeñoReport;
import com.mycompany.corporate.talent.hub.control.flujo.modelo.Desarrollador;
import com.mycompany.corporate.talent.hub.control.flujo.modelo.Empleado;
import com.mycompany.corporate.talent.hub.control.flujo.modelo.Gerente;
import com.mycompany.corporate.talent.hub.control.flujo.modelo.Promocionable;
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
                        case 6 -> calcularBonoAscenso(scanner);
                        case 7 -> emitirReportesDesempeno();
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
                6. Calcular bono de ascenso (interfaz Promocionable)
                7. Emitir reportes de desempeño (Records inmutables)
                0. Salir
                """);
    }

    private void registrar(Scanner scanner) {
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
        scanner.nextLine();

        // TASK 1/3 - Empleado es abstracta (sealed): se construye siempre a
        // través de uno de sus dos roles permitidos, Desarrollador o Gerente.
        System.out.println("Tipo de perfil: 1) Desarrollador   2) Gerente");
        System.out.print("Seleccione una opción: ");
        var tipoPerfil = scanner.nextInt();
        scanner.nextLine();

        Empleado nuevo;
        switch (tipoPerfil) {
            case 1 -> {
                System.out.print("Lenguaje principal: ");
                var lenguaje = scanner.nextLine().trim();
                nuevo = new Desarrollador(id, nombre, (byte) edadIngresada, salario, lenguaje);
            }
            case 2 -> {
                System.out.print("Presupuesto mensual a cargo: ");
                var presupuesto = scanner.nextDouble();
                scanner.nextLine();
                nuevo = new Gerente(id, nombre, (byte) edadIngresada, salario, presupuesto);
            }
            default -> {
                System.out.println("Tipo de perfil inválido.");
                return;
            }
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

            // TASK 3 - Sintaxis Moderna: Pattern Matching for instanceof.
            // Sin casting manual: la variable ya queda casteada y lista para
            // usarse dentro del propio if (ver modelo.legacy.ValidadorLegacy
            // para el contraste con instanceof + cast manual).
            if (empleado instanceof Desarrollador desarrollador) {
                System.out.print("| Rol: Desarrollador (" + desarrollador.getLenguajePrincipal() + ") ");
            } else if (empleado instanceof Gerente gerente) {
                System.out.printf("| Rol: Gerente (presupuesto: %.2f) ", gerente.getPresupuestoMensual());
            }

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

    /**
     * TASK 3/4 - Busca el empleado por id y, mediante Pattern Matching for
     * instanceof sobre la interfaz Promocionable, calcula su bono de
     * ascenso sin necesitar casting manual. registrarLogAscenso() es el
     * método default de la interfaz (Java 8): añadido a Promocionable sin
     * romper a Desarrollador ni a Gerente, que ya la implementaban.
     */
    private void calcularBonoAscenso(Scanner scanner) {
        if (servicio.getCantidadEmpleados() == 0) {
            System.out.println("Todavía no hay empleados registrados.");
            return;
        }

        System.out.print("ID del empleado: ");
        var id = scanner.nextInt();
        scanner.nextLine();

        try {
            Empleado empleado = servicio.obtenerEmpleadoPorId(id);
            if (empleado instanceof Promocionable promocionable) {
                promocionable.registrarLogAscenso();
                System.out.printf("Bono de ascenso para %s: %.2f%n",
                        empleado.getNombre(), promocionable.calcularBonoAscenso());
            } else {
                System.out.println("Este perfil no es promocionable.");
            }
        } catch (IllegalArgumentException excepcion) {
            System.out.println(excepcion.getMessage());
        }
    }

    /**
     * TASK 2 - Emite los reportes de fin de mes como Records inmutables
     * (modelo.DesempeñoReport): una vez generados, no pueden modificarse.
     */
    private void emitirReportesDesempeno() {
        if (servicio.getCantidadEmpleados() == 0) {
            System.out.println("Todavía no hay empleados registrados.");
            return;
        }

        System.out.println("\nREPORTES DE DESEMPEÑO (records inmutables)");
        for (DesempeñoReport reporte : servicio.generarReportesDesempeno()) {
            System.out.println(reporte);
        }
    }
}