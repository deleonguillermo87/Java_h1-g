package com.riwi.talent.model.legacy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * TASK 1 - Sintaxis Legacy (Java 8 hacia atrás): cierre manual con finally.
 *
 * ANÁLISIS:
 * Antes de try-with-resources (Java 7+), Connection, PreparedStatement y
 * ResultSet debían declararse fuera del try, inicializados en null, para
 * poder cerrarlos "a mano" dentro de un bloque finally. Además, close()
 * puede lanzar su propia SQLException, así que en rigor cada cierre
 * necesita su propio try/catch anidado dentro del finally (si no, una
 * excepción al cerrar el ResultSet podría impedir que se cierren el
 * PreparedStatement y la Connection).
 *
 * Riesgos de este estilo:
 *  - Si el programador olvida cerrar alguno de los tres recursos -algo
 *    fácil de que ocurra al ser manual y repetitivo-, la conexión queda
 *    abierta indefinidamente: Memory Leak y agotamiento del pool de
 *    conexiones de la base de datos.
 *  - Si ocurre una excepción entre la apertura de dos recursos, el
 *    primero puede quedar sin cerrar si el bloque finally no está bien
 *    anidado.
 *
 * Comparar con modelo.conexion.ConexionBD + EmpleadoDAOImpl, donde
 * try-with-resources cierra los tres recursos automáticamente -en orden
 * inverso al de apertura y sin importar si hubo excepción- eliminando
 * por completo este código repetitivo y propenso a errores.
 */
public class ConexionLegacyDemo {

    public void consultaLegacy(String url, String sql) {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = DriverManager.getConnection(url);
            sentencia = conexion.prepareStatement(sql);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                // procesar cada fila del resultado...
            }
        } catch (SQLException excepcion) {
            System.out.println("Error de base de datos: " + excepcion.getMessage());
        } finally {
            // Cierre manual, en orden inverso al de apertura. Cada recurso
            // se protege con su propio try/catch para que un fallo al
            // cerrar uno no impida intentar cerrar los siguientes.
            if (resultado != null) {
                try {
                    resultado.close();
                } catch (SQLException ignorada) {
                    // se ignora: ya estamos en la limpieza final
                }
            }
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException ignorada) {
                    // se ignora: ya estamos en la limpieza final
                }
            }
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException ignorada) {
                    // se ignora: ya estamos en la limpieza final
                }
            }
        }
    }
}
