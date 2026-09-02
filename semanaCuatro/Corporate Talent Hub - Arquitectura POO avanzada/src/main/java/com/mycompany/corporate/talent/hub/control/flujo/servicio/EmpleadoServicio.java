/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.corporate.talent.hub.control.flujo.servicio;

import com.mycompany.corporate.talent.hub.control.flujo.modelo.DesempeñoReport;
import com.mycompany.corporate.talent.hub.control.flujo.modelo.Empleado;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class EmpleadoServicio {
    private final List<Empleado> empleados = new ArrayList<>();
    private final Map<String , Empleado> empleadosPorId = new HashMap<>();
    private final Map<String , double[]> calificacionesPorId = new HashMap<>();
    private static final int CANTIDAD_TRIMESTRES = 3;
    private static final double PROMEDIO_PARA_PROMOCION = 80.0;
    

    public int getCantidadEmpleados() {
        return empleados.size();
    }
    
    /*
 * Sequenced Collections (Java 21):
 *
 * 1. getFirst()/getLast() expresan la intención directamente, sin calcular
 *    índices manualmente como en Java 8/11 (lista.get(0), 
 *    lista.get(lista.size()-1)).
 *
 * 2. Eliminan errores "off-by-one" (equivocarse en el -1, o acceder a una
 *    posición que no existe).
 *
 * 3. Si la lista está vacía, lanzan NoSuchElementException, un error más
 *    claro que el genérico IndexOutOfBoundsException del acceso por índice.
 *
 * 4. reversed() devuelve una vista invertida en O(1), sin copiar ni
 *    reordenar datos manualmente como se hacía antes con Collections.reverse().
 */
    public Empleado obtenerPrimerEmpleado() {
        return empleados.getFirst();
    }

    public Empleado obtenerUltimoEmpleado() {
        return empleados.getLast();
    }

    public List<Empleado> obtenerEmpleadosEnOrdenInverso() {
        return empleados.reversed();
    }
    

   

    public int getCantidadTrimestres() {
        return CANTIDAD_TRIMESTRES;
    }

    public boolean idRepetido(int idBuscado) {
        return empleadosPorId.containsKey(String.valueOf(idBuscado));
    }

    /**
     * TASK 3 - Punto único de búsqueda por id, usado desde la vista para
     * aplicar Pattern Matching for instanceof (ver EmpleadoVista) sin
     * necesidad de exponer el Map interno.
     */
    public Empleado obtenerEmpleadoPorId(int id) {
        Empleado empleado = empleadosPorId.get(String.valueOf(id));
        if (empleado == null) {
            throw new IllegalArgumentException("No existe un empleado con el id: " + id);
        }
        return empleado;
    }

    public void guardarEmpleado(Empleado empleado, double[] notas) {
        empleados.add(empleado);
        empleadosPorId.put(String.valueOf(empleado.getId()), empleado);
        calificacionesPorId.put(String.valueOf(empleado.getId()), notas);
        
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public  double calcularPromedio(int id) {
        double[] notas = calificacionesPorId.get(String.valueOf(id));
        
        if (notas == null){
            throw new  IllegalArgumentException("No existe un empleado con el id: " + id);
        }
        var suma = 0.0;
        for (int i = 0 ; i <notas.length; i++){
            suma += notas[i];
            
        }
        return suma / notas.length;
        
    }
    
    public boolean eliminarEmpleado(int id){
        if (!idRepetido(id)){
            throw new IllegalArgumentException("No existe un empleado con el id: " + id);    
        }
        
        String clave = String.valueOf(id);
        Empleado empleadoEliminado = empleadosPorId.remove(clave);
        empleados.remove(empleadoEliminado);
        calificacionesPorId.remove(clave);
        
        return true;
    }
    
    // ===== Filtrado avanzado y tipado con var =====
    
    public void eliminarEmpleadosBajoPromedio(double puntajeMinimo) {
        var idsAEliminar = new ArrayList<Integer>();
        
        for (var empleado : empleados) {
            if (calcularPromedio(empleado.getId()) < puntajeMinimo) {
                idsAEliminar.add(empleado.getId());
            }
        }
        
        for (var id : idsAEliminar) {
            eliminarEmpleado(id);
        }
    }
    
    public String generarReporteFinal() {
        var totalEmpleados = empleados.size();
        var sumaSalarios = 0.0;
        
        for (var empleado : empleados) {
            sumaSalarios += empleado.getSalario();
        }
        
        var promedioSalarios = totalEmpleados == 0 ? 0.0 : sumaSalarios / totalEmpleados;
        
        return String.format(
            "=== REPORTE FINAL ===\nTotal de empleados: %d\nPromedio de salarios: %.2f",
            totalEmpleados, promedioSalarios
        );
    }

    /**
     * TASK 2 - Sintaxis Moderna: construye un DesempeñoReport (record) por
     * cada empleado. Al ser inmutable, el reporte de fin de mes que se
     * entrega no puede alterarse después de emitido.
     */
    public List<DesempeñoReport> generarReportesDesempeno() {
        var reportes = new ArrayList<DesempeñoReport>();

        for (var empleado : empleados) {
            var promedio = calcularPromedio(empleado.getId());
            var feedback = promedio >= PROMEDIO_PARA_PROMOCION
                    ? "Aplica para promoción"
                    : "Sin cambios este periodo";
            reportes.add(new DesempeñoReport(empleado.getId(), promedio, feedback));
        }

        return reportes;
    }
}

