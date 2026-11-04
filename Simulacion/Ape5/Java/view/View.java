package view;

import java.util.List;
import java.util.Locale;
import model.Model.LCGScenario;
import model.Model.PeriodAnalysisResult;
import model.Model.SimulationRun;

public class View {

    public void showFirstObservations(SimulationRun run, int limit) {
        LCGScenario sc = run.getScenario();
        List<Long> xVals = run.getXValues();
        List<Double> uVals = run.getUValues();

        System.out.println("=================================================================");
        System.out.println("ESCENARIO: " + sc.getName());
        System.out.printf("Parámetros: a=%d, c=%d, m=%d, semilla=%d%n",
                sc.getA(), sc.getC(), sc.getM(), sc.getSeed());
        System.out.println("-----------------------------------------------------------------");
        System.out.printf("%-6s | %-15s | %-20s%n", "n", "X(n)", "U(n)");
        System.out.println("-----------------------------------------------------------------");

        int count = Math.min(limit, xVals.size());
        for (int i = 0; i < count; i++) {
            System.out.printf(Locale.US, "%-6d | %-15d | %-20.12f%n",
                    i + 1, xVals.get(i), uVals.get(i));
        }
        System.out.println("=================================================================");
    }

    public void showPeriodAnalysis(SimulationRun run) {
        PeriodAnalysisResult an = run.getAnalysis();
        System.out.println("\n[Análisis de Período - " + run.getScenario().getName() + "]");
        System.out.println("  Muestra evaluada        : " + an.getSampleSize() + " valores");
        System.out.println("  Valores únicos en muestra: " + an.getUniqueValues());

        if (an.isEmpiricalPeriodFound()) {
            System.out.printf("  Ciclo empírico detectado: Sí (índice %d, longitud = %d)%n",
                    an.getFirstRepeatIndex(), an.getCycleLength());
        } else {
            System.out.println("  Ciclo empírico detectado: No se repiten valores dentro de los 10 000 datos.");
        }

        System.out.println("  Cumple Hull-Dobell      : " + (an.isHullDobellFullPeriod() ? "Sí" : "No"));
        if (an.isHullDobellFullPeriod()) {
            System.out.println("  Período teórico exacto  : m = " + an.getTheoreticalPeriod() + " (2^32)");
        }
        System.out.println();
    }

    public void showSeedComparison(SimulationRun runA, SimulationRun runB, int limit) {
        System.out.println("================================================================================");
        System.out.printf("COMPARACIÓN DE LAS PRIMERAS %d OBSERVACIONES (Semilla 1 = %d vs Semilla 2 = %d)%n",
                limit, runA.getScenario().getSeed(), runB.getScenario().getSeed());
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-4s | %-12s | %-14s || %-12s | %-14s | %-12s%n",
                "n", "X1(n)", "U1(n)", "X2(n)", "U2(n)", "|U1 - U2|");
        System.out.println("--------------------------------------------------------------------------------");

        int count = Math.min(limit, Math.min(runA.getXValues().size(), runB.getXValues().size()));
        for (int i = 0; i < count; i++) {
            double u1 = runA.getUValues().get(i);
            double u2 = runB.getUValues().get(i);
            double diff = Math.abs(u1 - u2);
            System.out.printf(Locale.US, "%-4d | %-12d | %-14.10f || %-12d | %-14.10f | %-12.10f%n",
                    i + 1, runA.getXValues().get(i), u1, runB.getXValues().get(i), u2, diff);
        }
        System.out.println("================================================================================");
    }
}