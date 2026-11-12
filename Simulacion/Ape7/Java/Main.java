import controller.ChiController;
import controller.TableController;
import java.util.ArrayList;
import java.util.List;
import model.Escenario;
import service.Calculator;
import service.MonteCarloGenerator;
import timer.TimeAnalyze;
import timer.Timer;
import view.ChiView;
import view.TableView;

public class Main {
    public static void main(String[] args) {
        final long SEMILLA = 123456789L;
        final int[] TAMANOS_MUESTRA = {1000, 10000, 100000, 1000000};
        final int INTERVALOS_CHI = 10;

        Timer reloj = new Timer();
        TimeAnalyze analizadorTiempo = new TimeAnalyze();
        MonteCarloGenerator generador = new MonteCarloGenerator(SEMILLA);
        Calculator calculadora = new Calculator(generador, reloj);

        List<Escenario> escenarios = new ArrayList<>();
        for (int n : TAMANOS_MUESTRA) {
            escenarios.add(calculadora.ejecutarEscenario(n, INTERVALOS_CHI));
        }

        TableController tableController = new TableController(new TableView(), analizadorTiempo);
        ChiController chiController = new ChiController(new ChiView());

        tableController.procesarYMostrar(escenarios);
        chiController.procesarYMostrar(escenarios);
    }
}