package com.riwi.talent.model.legacy;

import java.util.Objects;

/**
 * Sintaxis Legacy (Java 8/11): POJO tradicional. Representar la misma
 * información que el record moderno modelo.DesempeñoReport exige aquí
 * ~35 líneas: campos, constructor, getters, equals, hashCode y toString
 * escritos a mano, sin ninguna garantía de inmutabilidad del lenguaje.
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
