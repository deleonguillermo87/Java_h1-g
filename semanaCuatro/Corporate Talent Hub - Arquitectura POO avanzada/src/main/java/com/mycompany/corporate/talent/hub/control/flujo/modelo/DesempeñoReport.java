package com.mycompany.corporate.talent.hub.control.flujo.modelo;

/**
 * TASK 2 - Sintaxis Moderna (Java 17/21): Record.
 *
 * ANÁLISIS:
 * Una sola línea genera automáticamente constructor, accessors
 * (idEmpleado(), promedio(), feedback()), equals(), hashCode() y toString().
 * Sus componentes son implícitamente private y final: inmutabilidad
 * garantizada por el propio lenguaje, ideal para emitir reportes de fin de
 * mes que no deben poder modificarse una vez generados (ver
 * modelo.legacy.ReporteDesempenoLegacy para el contraste con el POJO
 * tradicional).
 */
public record DesempeñoReport(int idEmpleado, double promedio, String feedback) {
}
