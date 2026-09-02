package com.mycompany.corporate.talent.hub.control.flujo.modelo.legacy;

import java.util.Objects;

/**
 * TASK 2 - Sintaxis Legacy (Java 8/11): POJO tradicional.
 *
 * ANÁLISIS:
 * Representar la misma información que el record moderno
 * modelo.DesempeñoReport exige aquí ~35 líneas: campos, constructor,
 * getters, equals, hashCode y toString escritos a mano. Además, nada
 * impide añadir un setter mañana y romper la inmutabilidad del reporte.
 */
public class ReporteDesempenoLegacy {

    private final int idEmpleado;
    private final double promedio;
    private final String feedback;

    public ReporteDesempenoLegacy(int idEmpleado, double promedio, String feedback) {
        this.idEmpleado = idEmpleado;
        this.promedio = promedio;
        this.feedback = feedback;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public double getPromedio() {
        return promedio;
    }

    public String getFeedback() {
        return feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReporteDesempenoLegacy)) {
            return false;
        }
        ReporteDesempenoLegacy that = (ReporteDesempenoLegacy) o;
        return idEmpleado == that.idEmpleado
                && Double.compare(that.promedio, promedio) == 0
                && Objects.equals(feedback, that.feedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmpleado, promedio, feedback);
    }

    @Override
    public String toString() {
        return "ReporteDesempenoLegacy{idEmpleado=" + idEmpleado
                + ", promedio=" + promedio
                + ", feedback='" + feedback + "'}";
    }
}
