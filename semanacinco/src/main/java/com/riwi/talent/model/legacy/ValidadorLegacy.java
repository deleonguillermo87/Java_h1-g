package com.riwi.talent.model.legacy;

/**
 * Sintaxis Legacy (Java 8/11): instanceof + casting manual. Se pregunta el
 * tipo con instanceof y luego, en una línea aparte, hay que castear
 * explícitamente para acceder a los métodos propios de la subclase.
 * Comparar con el uso de Pattern Matching for instanceof en
 * EmpleadoDAOImpl y EmpleadoVista, que no necesitan ningún casting.
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
