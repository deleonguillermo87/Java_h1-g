package com.riwi.talent.model.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * TASK 1 - Clase de utilidad para la conexión JDBC (Java 17/21).
 *
 * ANÁLISIS:
 * Esta clase solo entrega una Connection nueva por cada llamada; cerrarla
 * -junto con cualquier PreparedStatement/ResultSet abierto a partir de
 * ella- es responsabilidad de quien la use, mediante try-with-resources
 * (ver EmpleadoDAOImpl). Al declarar los recursos en el paréntesis del
 * try, Java los cierra automáticamente -en orden inverso al de apertura-
 * en cuanto el bloque termina, sea de forma normal o por una excepción.
 * Así ningún Statement ni ResultSet queda "colgado" en memoria, que es
 * justamente el Memory Leak que sí es posible con el cierre manual en
 * finally (ver modelo.legacy.ConexionLegacyDemo).
 */
public final class ConexionBD {

    private static final String URL = "jdbc:sqlite:talent_hub.db";

    private ConexionBD() {
    }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
