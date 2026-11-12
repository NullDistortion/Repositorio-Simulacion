package controller;

import java.util.List;
import model.Escenario;
import timer.TimeAnalyze;
import view.TableView;

public class TableController {
    private final TableView vista;
    private final TimeAnalyze analizadorTiempo;

    public TableController(TableView vista, TimeAnalyze analizadorTiempo) {
        this.vista = vista;
        this.analizadorTiempo = analizadorTiempo;
    }

    public void procesarYMostrar(List<Escenario> escenarios) {
        analizadorTiempo.analizarTiempos(escenarios);
        vista.mostrarResultados(escenarios, analizadorTiempo);
    }
}