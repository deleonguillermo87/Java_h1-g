package com.riwi.talent;

import com.riwi.talent.controller.EmpleadoController;
import com.riwi.talent.model.dao.EmpleadoDAOImpl;
import com.riwi.talent.view.EmpleadoVista;

public class App {
    public static void main(String[] args) {
        var empleadoDAO = new EmpleadoDAOImpl();
        var controlador = new EmpleadoController(empleadoDAO);
        var vista = new EmpleadoVista(controlador);
        vista.iniciar();
    }
}
