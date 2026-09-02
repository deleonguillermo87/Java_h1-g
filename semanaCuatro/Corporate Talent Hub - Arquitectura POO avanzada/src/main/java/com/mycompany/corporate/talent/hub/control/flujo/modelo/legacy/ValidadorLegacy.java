package com.mycompany.corporate.talent.hub.control.flujo.modelo.legacy;

/**
 * TASK 3 - Sintaxis Legacy (Java 8/11): instanceof + casting manual.
 *
 * ANÁLISIS:
 * Primero se pregunta el tipo con instanceof y luego, en una línea aparte,
 * se debe castear explícitamente el objeto para poder llamar a los métodos
 * propios de la subclase: ((DesarrolladorLegacy) p).getLenguajePrincipal().
 * Si alguien olvida el cast no compila; si el cast es incorrecto, se lanza
 * un ClassCastException en tiempo de ejecución. Compárese con
 * modelo.vista.EmpleadoVista.mostrarReporte(), que usa Pattern Matching for
 * instanceof y no necesita ningún casting.
 */
public class ValidadorLegacy {

    public String describir(EmpleadoLegacy p) {
        if (p instanceof DesarrolladorLegacy) {
            return "Desarrollador -> lenguaje: " + ((DesarrolladorLegacy) p).getLenguajePrincipal();
        } else if (p instanceof GerenteLegacy) {
            return "Gerente -> presupuesto: " + ((GerenteLegacy) p).getPresupuestoMensual();
        }
        return "Empleado sin rol específico";
    }
}
