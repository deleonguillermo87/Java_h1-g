package com.riwi.talent.controller;

import com.riwi.talent.model.Desarrollador;
import com.riwi.talent.model.DesempeñoReport;
import com.riwi.talent.model.Empleado;
import com.riwi.talent.model.Gerente;
import com.riwi.talent.model.Promocionable;
import com.riwi.talent.model.dao.EmpleadoDAO;
import java.sql.SQLException;
import java.util.List;

/**
 * TASK 3 - Controlador (MVC): mediador entre la Vista y el modelo/DAO.
 * No contiene ningún Scanner ni System.out de interacción con el usuario:
 * recibe datos ya validados como parámetros, coordina las llamadas al DAO
 * y devuelve resultados (objetos de dominio, listas, texto) para que sea
 * la Vista quien decida cómo mostrarlos.
 */
public class EmpleadoController {

    private final EmpleadoDAO empleadoDAO;

    public EmpleadoController(EmpleadoDAO empleadoDAO) {
        this.empleadoDAO = empleadoDAO;
    }

    public void inicializar() throws SQLException {
        empleadoDAO.inicializarEsquema();
    }

    public boolean registrarDesarrollador(int id, String nombre, byte edad, double salario,
            String lenguajePrincipal, double[] calificaciones) throws SQLException {
        if (empleadoDAO.buscarPorId(id) != null) {
            return false;
        }
        var desarrollador = new Desarrollador(id, nombre, edad, salario, lenguajePrincipal);
        return empleadoDAO.insertar(desarrollador, calificaciones);
    }

    public boolean registrarGerente(int id, String nombre, byte edad, double salario,
            double presupuestoMensual, double[] calificaciones) throws SQLException {
        if (empleadoDAO.buscarPorId(id) != null) {
            return false;
        }
        var gerente = new Gerente(id, nombre, edad, salario, presupuestoMensual);
        return empleadoDAO.insertar(gerente, calificaciones);
    }

    public List<Empleado> listarEmpleados() throws SQLException {
        return empleadoDAO.listar();
    }

    public Empleado buscarPorId(int id) throws SQLException {
        return empleadoDAO.buscarPorId(id);
    }

    public boolean actualizarSalario(int id, double nuevoSalario) throws SQLException {
        return empleadoDAO.actualizarSalario(id, nuevoSalario);
    }

    public boolean eliminarEmpleado(int id) throws SQLException {
        return empleadoDAO.eliminar(id);
    }

    public List<DesempeñoReport> generarReportesDesempeno() throws SQLException {
        return empleadoDAO.obtenerReportesDesempeno();
    }

    public double calcularBonoAscenso(Empleado empleado) {
        if (empleado instanceof Promocionable promocionable) {
            promocionable.registrarLogAscenso();
            return promocionable.calcularBonoAscenso();
        }
        throw new IllegalStateException("Este perfil no es promocionable.");
    }

    /**
     * TASK 4 - Reporte final consolidado con Text Blocks: junta el total
     * de empleados, el promedio de salarios y el detalle de desempeño
     * (records) leídos directamente de la base de datos.
     */
    public String generarReporteFinal() throws SQLException {
        var empleados = empleadoDAO.listar();
        var reportes = empleadoDAO.obtenerReportesDesempeno();

        var totalEmpleados = empleados.size();
        var sumaSalarios = 0.0;
        for (var empleado : empleados) {
            sumaSalarios += empleado.getSalario();
        }
        var promedioSalarios = totalEmpleados == 0 ? 0.0 : sumaSalarios / totalEmpleados;

        var detalle = new StringBuilder();
        for (var reporte : reportes) {
            detalle.append("  · Empleado #%d -> promedio %.2f (%s)%n"
                    .formatted(reporte.idEmpleado(), reporte.promedio(), reporte.feedback()));
        }
        if (detalle.isEmpty()) {
            detalle.append("  (sin calificaciones registradas todavía)\n");
        }

        return """
                =====================================
                       REPORTE FINAL CONSOLIDADO
                =====================================
                Total de empleados persistidos: %d
                Promedio general de salarios:   %.2f

                Desempeño por empleado:
                %s""".formatted(totalEmpleados, promedioSalarios, detalle);
    }
}
