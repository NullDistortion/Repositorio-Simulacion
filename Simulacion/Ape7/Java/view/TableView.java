package view;

import java.util.List;
import java.util.Locale;
import model.Escenario;
import timer.TimeAnalyze;

public class TableView {
    public void mostrarResultados(List<Escenario> escenarios, TimeAnalyze analisisTiempo) {
        System.out.println("\n=== RESULTADOS MONTE CARLO EN JAVA (ESTIMACION DE PI) ===");
        String encabezado = String.format("%10s | %12s | %12s | %17s | %9s",
                "N", "Pi Estimado", "Error Abs.", "Tiempo Java (s)", "% Tiempo");
        System.out.println(encabezado);
        System.out.println("-".repeat(encabezado.length()));

        for (Escenario e : escenarios) {
            double pct = analisisTiempo.getPorcentajePorEscenario().getOrDefault(e.getNPuntos(), 0.0);
            System.out.printf(Locale.US, "%10d | %12.6f | %12.6f | %17.6f | %8.2f%%%n",
                    e.getNPuntos(), e.getPiEstimado(), e.getErrorAbsoluto(), e.getTiempoJava(), pct);
        }

        System.out.println("-".repeat(encabezado.length()));
        System.out.printf(Locale.US, "Tiempo total de simulacion : %.6f s%n", analisisTiempo.getTiempoTotal());
        System.out.printf(Locale.US, "Tiempo promedio por muestra: %.6f s%n%n", analisisTiempo.getTiempoPromedio());
    }
}