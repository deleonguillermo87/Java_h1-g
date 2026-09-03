package com.riwi.talent.model.dao;

import com.riwi.talent.model.DesempeñoReport;
import com.riwi.talent.model.Empleado;
import java.sql.SQLException;
import java.util.List;

/**
 * TASK 2 - Contrato de acceso a datos para la entidad Empleado (roles
 * Desarrollador/Gerente). Separar la interfaz de su implementación
 * permite sustituir EmpleadoDAOImpl (SQLite) por otra implementación
 * -otra base de datos, o un mock para pruebas- sin tocar al Controlador,
 * que solo conoce este contrato.
 */
public interface EmpleadoDAO {

    void inicializarEsquema() throws SQLException;

    boolean insertar(Empleado empleado, double[] calificaciones) throws SQLException;

    List<Empleado> listar() throws SQLException;

    Empleado buscarPorId(int id) throws SQLException;

    boolean actualizarSalario(int id, double nuevoSalario) throws SQLException;

    boolean eliminar(int id) throws SQLException;

    /**
     * TASK 4 - Consulta SELECT compleja (JOIN + AVG + GROUP BY) mapeada
     * directamente a records DesempeñoReport.
     */
    List<DesempeñoReport> obtenerReportesDesempeno() throws SQLException;
}
