/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.documentacion;

/**
 * Arquitectura y evolución del Ecosistema.
 */
public class NotasArquitectura {
    /*
     * DIFERENCIA ENFOQUE JAVA 8 VS JAVA 17/21:
     * - Java 8 (Legacy): Se enfocó en la llegada de lambdas y streams de forma funcional.
     * - Java 17/21 (LTS Actual): Introduce Records para simplificar datos, Text Blocks y Virtual Threads para concurrencia masiva.
     * 
     * GESTIÓN DE MEMORIA (JVM):
     * - Los objetos instanciados con 'new' se alojan en la memoria Heap.
     * - Los Garbage Collectors modernos (G1GC / ZGC) gestionan la memoria por regiones dinámicas,
     *   barriendo objetos sin referencias de forma concurrente para eliminar pausas en el sistema.
     */
}
