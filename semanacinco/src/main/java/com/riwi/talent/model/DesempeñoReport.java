package com.riwi.talent.model;

/**
 * TASK 4 - Record (Java 17/21) reutilizado para mapear los resultados de
 * una consulta SELECT compleja (JOIN + AVG + GROUP BY, ver
 * EmpleadoDAOImpl.obtenerReportesDesempeno()).
 *
 * ANÁLISIS:
 * Combinar Records con JDBC moderno reduce drásticamente el código de
 * mantenimiento frente a un POJO tradicional (Java 8): no hace falta
 * escribir a mano el constructor, los getters, equals/hashCode ni
 * toString solo para transportar tres columnas de un ResultSet. El
 * record además es inmutable por diseño, lo cual es justamente lo que se
 * espera de un reporte de solo lectura: una vez leído de la base de
 * datos, nada en el código puede alterarlo por accidente.
 */
public record DesempeñoReport(int idEmpleado, double promedio, String feedback) {
}
