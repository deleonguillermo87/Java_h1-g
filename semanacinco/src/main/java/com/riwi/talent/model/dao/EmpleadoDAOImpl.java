package com.riwi.talent.model.dao;

import com.riwi.talent.model.Desarrollador;
import com.riwi.talent.model.DesempeñoReport;
import com.riwi.talent.model.Empleado;
import com.riwi.talent.model.Gerente;
import com.riwi.talent.model.conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * TASK 2 - Implementación física del DAO.
 *
 * TASK 1 - Cada método abre sus propios recursos JDBC dentro de un
 * try-with-resources: Connection, PreparedStatement y, cuando aplica,
 * ResultSet se cierran automáticamente al salir del bloque -en orden
 * inverso al de apertura- sin importar si el método termina normal o por
 * excepción. Eso es justamente lo que previene los Memory Leaks: ningún
 * recurso puede quedar abierto "olvidado", porque el propio compilador
 * genera el código de cierre (a diferencia del finally manual, ver
 * modelo.legacy.ConexionLegacyDemo).
 *
 * Seguridad: todas las sentencias usan PreparedStatement con parámetros
 * (?) y nunca concatenación de Strings, por lo que el sistema queda
 * protegido contra inyección SQL.
 */
public class EmpleadoDAOImpl implements EmpleadoDAO {

    private static final double PROMEDIO_PARA_PROMOCION = 80.0;

    @Override
    public void inicializarEsquema() throws SQLException {
        var sqlEmpleados = """
                CREATE TABLE IF NOT EXISTS empleados (
                    id INTEGER PRIMARY KEY,
                    nombre TEXT NOT NULL,
                    edad INTEGER NOT NULL,
                    salario REAL NOT NULL,
                    tipo_perfil TEXT NOT NULL,
                    lenguaje_principal TEXT,
                    presupuesto_mensual REAL
                )""";

        var sqlCalificaciones = """
                CREATE TABLE IF NOT EXISTS calificaciones (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    id_empleado INTEGER NOT NULL,
                    trimestre INTEGER NOT NULL,
                    calificacion REAL NOT NULL,
                    FOREIGN KEY (id_empleado) REFERENCES empleados(id) ON DELETE CASCADE
                )""";

        // TASK 1 - try-with-resources: Connection y Statement se cierran
        // solos al terminar el bloque, sin ningún finally manual.
        try (Connection conexion = ConexionBD.obtenerConexion();
             Statement sentencia = conexion.createStatement()) {
            sentencia.execute(sqlEmpleados);
            sentencia.execute(sqlCalificaciones);
        }
    }

    @Override
    public boolean insertar(Empleado empleado, double[] calificaciones) throws SQLException {
        var sqlEmpleado = """
                INSERT INTO empleados (id, nombre, edad, salario, tipo_perfil, lenguaje_principal, presupuesto_mensual)
                VALUES (?, ?, ?, ?, ?, ?, ?)""";
        var sqlCalificacion = "INSERT INTO calificaciones (id_empleado, trimestre, calificacion) VALUES (?, ?, ?)";

        // TASK 1 - Un único try-with-resources abarca la Connection y los
        // dos PreparedStatement: los tres se cierran automáticamente al
        // salir del bloque, sin importar si la inserción tuvo éxito o no.
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentenciaEmpleado = conexion.prepareStatement(sqlEmpleado);
             PreparedStatement sentenciaCalificacion = conexion.prepareStatement(sqlCalificacion)) {

            sentenciaEmpleado.setInt(1, empleado.getId());
            sentenciaEmpleado.setString(2, empleado.getNombre());
            sentenciaEmpleado.setInt(3, empleado.getEdad());
            sentenciaEmpleado.setDouble(4, empleado.getSalario());

            // TASK 3 - Pattern Matching for instanceof: sin casting manual
            // para leer el atributo específico de cada rol.
            if (empleado instanceof Desarrollador desarrollador) {
                sentenciaEmpleado.setString(5, "DESARROLLADOR");
                sentenciaEmpleado.setString(6, desarrollador.getLenguajePrincipal());
                sentenciaEmpleado.setNull(7, Types.DOUBLE);
            } else if (empleado instanceof Gerente gerente) {
                sentenciaEmpleado.setString(5, "GERENTE");
                sentenciaEmpleado.setNull(6, Types.VARCHAR);
                sentenciaEmpleado.setDouble(7, gerente.getPresupuestoMensual());
            } else {
                return false;
            }

            sentenciaEmpleado.executeUpdate();

            for (var trimestre = 0; trimestre < calificaciones.length; trimestre++) {
                sentenciaCalificacion.setInt(1, empleado.getId());
                sentenciaCalificacion.setInt(2, trimestre + 1);
                sentenciaCalificacion.setDouble(3, calificaciones[trimestre]);
                sentenciaCalificacion.addBatch();
            }
            sentenciaCalificacion.executeBatch();

            return true;
        }
    }

    @Override
    public List<Empleado> listar() throws SQLException {
        var sql = """
                SELECT id, nombre, edad, salario, tipo_perfil, lenguaje_principal, presupuesto_mensual
                FROM empleados
                ORDER BY id""";
        var empleados = new ArrayList<Empleado>();

        // TASK 1 - Connection, PreparedStatement y ResultSet, los tres
        // recursos JDBC, en un mismo try-with-resources.
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                empleados.add(mapearEmpleado(resultado));
            }
        }

        return empleados;
    }

    @Override
    public Empleado buscarPorId(int id) throws SQLException {
        var sql = """
                SELECT id, nombre, edad, salario, tipo_perfil, lenguaje_principal, presupuesto_mensual
                FROM empleados
                WHERE id = ?""";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);

            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? mapearEmpleado(resultado) : null;
            }
        }
    }

    /**
     * TASK 3 - Pattern Matching en un switch de expresión (Java 21): sin
     * ningún casting, cada rama construye directamente el subtipo que
     * corresponde según la columna tipo_perfil.
     */
    private Empleado mapearEmpleado(ResultSet resultado) throws SQLException {
        var id = resultado.getInt("id");
        var nombre = resultado.getString("nombre");
        var edad = (byte) resultado.getInt("edad");
        var salario = resultado.getDouble("salario");
        var tipoPerfil = resultado.getString("tipo_perfil");

        return switch (tipoPerfil) {
            case "DESARROLLADOR" ->
                new Desarrollador(id, nombre, edad, salario, resultado.getString("lenguaje_principal"));
            case "GERENTE" ->
                new Gerente(id, nombre, edad, salario, resultado.getDouble("presupuesto_mensual"));
            default -> throw new SQLException("Tipo de perfil desconocido en la base de datos: " + tipoPerfil);
        };
    }

    @Override
    public boolean actualizarSalario(int id, double nuevoSalario) throws SQLException {
        var sql = "UPDATE empleados SET salario = ? WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setDouble(1, nuevoSalario);
            sentencia.setInt(2, id);

            return sentencia.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        var sqlCalificaciones = "DELETE FROM calificaciones WHERE id_empleado = ?";
        var sqlEmpleado = "DELETE FROM empleados WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentenciaCalificaciones = conexion.prepareStatement(sqlCalificaciones);
             PreparedStatement sentenciaEmpleado = conexion.prepareStatement(sqlEmpleado)) {

            sentenciaCalificaciones.setInt(1, id);
            sentenciaCalificaciones.executeUpdate();

            sentenciaEmpleado.setInt(1, id);
            return sentenciaEmpleado.executeUpdate() > 0;
        }
    }

    /**
     * TASK 4 - Consulta SELECT compleja: JOIN entre empleados y
     * calificaciones + AVG/GROUP BY, mapeada directamente al record
     * DesempeñoReport (ver el análisis completo en esa clase).
     */
    @Override
    public List<DesempeñoReport> obtenerReportesDesempeno() throws SQLException {
        var sql = """
                SELECT e.id AS id_empleado, AVG(c.calificacion) AS promedio
                FROM empleados e
                JOIN calificaciones c ON c.id_empleado = e.id
                GROUP BY e.id
                ORDER BY e.id""";

        var reportes = new ArrayList<DesempeñoReport>();

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                var idEmpleado = resultado.getInt("id_empleado");
                var promedio = resultado.getDouble("promedio");
                var feedback = promedio >= PROMEDIO_PARA_PROMOCION
                        ? "Aplica para promoción"
                        : "Sin cambios este periodo";
                reportes.add(new DesempeñoReport(idEmpleado, promedio, feedback));
            }
        }

        return reportes;
    }
}
