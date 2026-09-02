package com.mycompany.corporate.talent.hub.control.flujo.modelo;

/**
 * TASK 4 - Abstracción y evolución de interfaces.
 *
 * ANÁLISIS:
 * calcularBonoAscenso() es el contrato abstracto: cada rol que implemente
 * Promocionable decide su propia fórmula de bono.
 * registrarLogAscenso() es un método default (introducido en Java 8). Antes
 * de los métodos default, añadir un método nuevo a una interfaz existente
 * rompía a TODAS las clases que ya la implementaban. Con "default" se pudo
 * agregar este log sin obligar a Desarrollador, Gerente ni a ninguna otra
 * clase futura a reescribir código que ya funcionaba.
 */
public interface Promocionable {

    double calcularBonoAscenso();

    default void registrarLogAscenso() {
        System.out.println("[LOG] Bono de ascenso calculado: " + calcularBonoAscenso());
    }
}
