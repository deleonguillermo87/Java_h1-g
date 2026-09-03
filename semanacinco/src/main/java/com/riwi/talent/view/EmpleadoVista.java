package com.riwi.talent.view;

import com.riwi.talent.controller.EmpleadoController;
import com.riwi.talent.model.Desarrollador;
import com.riwi.talent.model.DesempeñoReport;
import com.riwi.talent.model.Empleado;
import com.riwi.talent.model.Gerente;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * TASK 3 - Vista (MVC): única capa donde se usa Scanner / System.out para
 * interactuar con el usuario. No conoce el DAO ni SQL: todo lo que
 * necesita se lo pide al Controlador.
 */
public class EmpleadoVista {

    private final EmpleadoController controlador;
    private static final double NOTA_MINIMA = 0.0;
    private static final double NOTA_MAXIMA = 100.0;
    private static final int CANTIDAD_TRIMESTRES = 3;

    public EmpleadoVista(EmpleadoController controlador) {
        this.controlador = controlador;
    }

    public void iniciar() {
        try {
            controlador.inicializar();
        } catch (SQLException excepcion) {
            System.out.println("No fue posible preparar la base de datos: " + excepcion.getMessage());
            return;
        }

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
                        case 2 -> mostrarEmpleados();
                        case 3 -> actualizarSalario(scanner);
                        case 4 -> eliminar(scanner);
                        case 5 -> mostrarReporteFinal();
                        case 6 -> calcularBonoAscenso(scanner);
                        case 7 -> emitirReportesDesempeno();
                        case 0 -> {
                            sistemaActivo = false;
                            System.out.println("Sesión finalizada. Los datos quedan persistidos en talent_hub.db.");
                        }
                        default -> System.out.println("Opción fuera del menú.");
                    }
                } catch (InputMismatchException excepcion) {
                    System.out.println("Entrada inválida. Debe escribir un valor numérico.");
                    scanner.nextLine();
                } catch (SQLException excepcion) {
                    System.out.println("Error de base de datos: " + excepcion.getMessage());
                }
            } while (sistemaActivo);
        }
    }

    private void mostrarMenu() {
        System.out.println("""
                =====================================
                  CORPORATE TALENT HUB (JDBC + MVC)
                =====================================
                1. Registrar empleado y calificaciones
                2. Listar empleados (desde la base de datos)
                3. Actualizar salario de un empleado
                4. Eliminar empleado
                5. Reporte final consolidado (Text Block)
                6. Calcular bono de ascenso (interfaz Promocionable)
                7. Reportes de desempeño (Records + SELECT complejo)
                0. Salir
                """);
    }

    private void registrar(Scanner scanner) throws SQLException {
        System.out.print("ID positivo: ");
        var id = scanner.nextInt();
        scanner.nextLine();

        if (id <= 0) {
            System.out.println("El ID debe ser mayor que cero.");
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

        System.out.println("Tipo de perfil: 1) Desarrollador   2) Gerente");
        System.out.print("Seleccione una opción: ");
        var tipoPerfil = scanner.nextInt();
        scanner.nextLine();

        var notas = new double[CANTIDAD_TRIMESTRES];
        boolean registrado;

        switch (tipoPerfil) {
            case 1 -> {
                System.out.print("Lenguaje principal: ");
                var lenguaje = scanner.nextLine().trim();
                if (!leerCalificaciones(scanner, notas)) {
                    return;
                }
                registrado = controlador.registrarDesarrollador(
                        id, nombre, (byte) edadIngresada, salario, lenguaje, notas);
            }
            case 2 -> {
                System.out.print("Presupuesto mensual a cargo: ");
                var presupuesto = scanner.nextDouble();
                scanner.nextLine();
                if (!leerCalificaciones(scanner, notas)) {
                    return;
                }
                registrado = controlador.registrarGerente(
                        id, nombre, (byte) edadIngresada, salario, presupuesto, notas);
            }
            default -> {
                System.out.println("Tipo de perfil inválido.");
                return;
            }
        }

        System.out.println(registrado
                ? "Empleado registrado y persistido en la base de datos."
                : "Ya existe un empleado con ese ID.");
    }

    private boolean leerCalificaciones(Scanner scanner, double[] notas) {
        for (var t = 0; t < notas.length; t++) {
            System.out.printf("Calificación del trimestre %d (0 a 100): ", t + 1);
            var calificacion = scanner.nextDouble();

            if (calificacion < NOTA_MINIMA || calificacion > NOTA_MAXIMA) {
                System.out.println("La calificación está fuera del rango permitido.");
                scanner.nextLine();
                return false;
            }
            notas[t] = calificacion;
        }
        scanner.nextLine();
        return true;
    }

    private void mostrarEmpleados() throws SQLException {
        List<Empleado> empleados = controlador.listarEmpleados();
        if (empleados.isEmpty()) {
            System.out.println("Todavía no hay empleados registrados.");
            return;
        }

        System.out.println("\nEMPLEADOS PERSISTIDOS EN LA BASE DE DATOS");
        for (Empleado empleado : empleados) {
            System.out.printf("ID: %d | Nombre: %s | Salario: %.2f ",
                    empleado.getId(), empleado.getNombre(), empleado.getSalario());

            // Pattern Matching for instanceof: sin casting manual (ver
            // modelo.legacy.ValidadorLegacy para el contraste).
            if (empleado instanceof Desarrollador desarrollador) {
                System.out.println("| Rol: Desarrollador (" + desarrollador.getLenguajePrincipal() + ")");
            } else if (empleado instanceof Gerente gerente) {
                System.out.printf("| Rol: Gerente (presupuesto: %.2f)%n", gerente.getPresupuestoMensual());
            }
        }
    }

    private void actualizarSalario(Scanner scanner) throws SQLException {
        System.out.print("ID del empleado a actualizar: ");
        var id = scanner.nextInt();
        System.out.print("Nuevo salario: ");
        var nuevoSalario = scanner.nextDouble();
        scanner.nextLine();

        if (nuevoSalario <= 0) {
            System.out.println("El salario debe ser mayor que cero.");
            return;
        }

        var actualizado = controlador.actualizarSalario(id, nuevoSalario);
        System.out.println(actualizado ? "Salario actualizado." : "No existe un empleado con ese ID.");
    }

    private void eliminar(Scanner scanner) throws SQLException {
        System.out.print("ID del empleado a eliminar: ");
        var id = scanner.nextInt();
        scanner.nextLine();

        var eliminado = controlador.eliminarEmpleado(id);
        System.out.println(eliminado ? "Empleado eliminado." : "No existe un empleado con ese ID.");
    }

    private void mostrarReporteFinal() throws SQLException {
        System.out.println(controlador.generarReporteFinal());
    }

    private void calcularBonoAscenso(Scanner scanner) throws SQLException {
        System.out.print("ID del empleado: ");
        var id = scanner.nextInt();
        scanner.nextLine();

        var empleado = controlador.buscarPorId(id);
        if (empleado == null) {
            System.out.println("No existe un empleado con ese ID.");
            return;
        }

        try {
            var bono = controlador.calcularBonoAscenso(empleado);
            System.out.printf("Bono de ascenso para %s: %.2f%n", empleado.getNombre(), bono);
        } catch (IllegalStateException excepcion) {
            System.out.println(excepcion.getMessage());
        }
    }

    private void emitirReportesDesempeno() throws SQLException {
        List<DesempeñoReport> reportes = controlador.generarReportesDesempeno();
        if (reportes.isEmpty()) {
            System.out.println("Todavía no hay calificaciones registradas.");
            return;
        }

        System.out.println("\nREPORTES DE DESEMPEÑO (records inmutables, SELECT con JOIN)");
        for (DesempeñoReport reporte : reportes) {
            System.out.println(reporte);
        }
    }
}
