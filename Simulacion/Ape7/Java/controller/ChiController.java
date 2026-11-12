package controller;

import java.util.List;
import model.Escenario;
import view.ChiView;

public class ChiController {
    private final ChiView vista;

    public ChiController(ChiView vista) {
        this.vista = vista;
    }

    public void procesarYMostrar(List<Escenario> escenarios) {
        vista.mostrarPruebaChi(escenarios);
        vista.graficarChiCuadrado(escenarios);
    }
}