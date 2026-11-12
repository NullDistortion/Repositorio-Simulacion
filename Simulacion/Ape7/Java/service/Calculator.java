package service;

import java.util.Map;
import model.Escenario;
import timer.Timer;

public class Calculator {
    private static final Map<Integer, Double> TABLA_CHI_ALFA_005 = Map.of(
        9, 16.9190,
        19, 30.1435,
        99, 123.2252
    );

    private final MonteCarloGenerator generador;
    private final Timer timer;

    public Calculator(MonteCarloGenerator generador, Timer timer) {
        this.generador = generador;
        this.timer = timer;
    }

    public Escenario ejecutarEscenario(int nPuntos, int kIntervalos) {
        // 1. Fase Monte Carlo cronometrada aisladamente
        generador.reiniciar();
        int puntosDentro = 0;

        timer.iniciar();
        for (int i = 0; i < nPuntos; i++) {
            double x = generador.siguienteUniforme();
            double y = generador.siguienteUniforme();
            if ((x * x + y * y) <= 1.0) {
                puntosDentro++;
            }
        }
        double tiempoEjecucion = timer.detener();

        double piEstimado = 4.0 * ((double) puntosDentro / nPuntos);
        double errorAbsoluto = Math.abs(piEstimado - Math.PI);

        // 2. Fase de validación Chi-cuadrado (2 * N números)
        generador.reiniciar();
        int totalNumeros = 2 * nPuntos;
        int[] frecuenciasObs = new int[kIntervalos];

        for (int i = 0; i < totalNumeros; i++) {
            double u = generador.siguienteUniforme();
            int indice = (int) (u * kIntervalos);
            if (indice == kIntervalos) {
                indice = kIntervalos - 1;
            }
            frecuenciasObs[indice]++;
        }

        double frecuenciaEsp = (double) totalNumeros / kIntervalos;
        double chiCalc = 0.0;
        for (int obs : frecuenciasObs) {
            double diff = obs - frecuenciaEsp;
            chiCalc += (diff * diff) / frecuenciaEsp;
        }

        int gl = kIntervalos - 1;
        double chiCrit = TABLA_CHI_ALFA_005.getOrDefault(gl, 16.9190);
        boolean cumple = chiCalc <= chiCrit;

        return new Escenario(
            nPuntos, puntosDentro, piEstimado, errorAbsoluto,
            tiempoEjecucion, totalNumeros, kIntervalos,
            frecuenciasObs, frecuenciaEsp, chiCalc, chiCrit, cumple
        );
    }
}