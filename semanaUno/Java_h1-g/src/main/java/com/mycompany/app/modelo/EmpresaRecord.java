/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.modelo;

/**
 * Record moderno (Java 17/21).
 * 
 * ANÁLISIS:
 * - Brevedad: Define campos, constructor, getters, equals, hashCode y toString en una sola línea.
 * - Inmutabilidad: Sus campos son implícitamente final. No pueden modificarse después de su creación.
 */
public record EmpresaRecord(String nombre, String nit, int anioFundacion) {
}
