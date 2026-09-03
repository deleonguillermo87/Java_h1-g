package com.riwi.talent.model;

/**
 * Abstracción y evolución de interfaces: calcularBonoAscenso() es el
 * contrato abstracto (cada rol decide su propia fórmula). registrarLogAscenso()
 * es un método default (Java 8): antes de los métodos default, añadir un
 * método nuevo a una interfaz existente rompía a todas las clases que ya
 * la implementaban. Con "default" se pudo agregar este log sin obligar a
 * Desarrollador ni a Gerente a reescribir código que ya funcionaba.
 */
public interface Promocionable {

    double calcularBonoAscenso();

    default void registrarLogAscenso() {
        System.out.println("[LOG] Bono de ascenso calculado: " + calcularBonoAscenso());
    }
}
