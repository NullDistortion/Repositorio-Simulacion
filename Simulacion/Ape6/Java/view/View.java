package Java.view;

import Java.model.Chi;
import Java.model.KS;
import java.util.Locale;

public class View {
    public void showGeneratedNumbers(double[] numbers, int previewLimit) {
        int total = numbers.length;
        System.out.printf("=== SECUENCIA GENERADA (Total: %d números) ===%n", total);
        if (total <= previewLimit) {
            System.out.println(formatSubArray(numbers, 0, total));
        } else {
            int half = previewLimit / 2;
            System.out.printf("Primeros %d: %s%n", half, formatSubArray(numbers, 0, half));
            System.out.printf("Últimos  %d: %s%n", half, formatSubArray(numbers, total - half, total));
        }
        System.out.println();
    }

    private String formatSubArray(double[] arr, int start, int end) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = start; i < end; i++) {
            sb.append(String.format(Locale.US, "%.6f", arr[i]));
            if (i < end - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public void showChiResults(Chi result) {
        System.out.println("=== RESULTADOS PRUEBA CHI-CUADRADO ===");
        System.out.printf("%-15s | %-22s | %-20s%n", "Intervalo", "Frec. Observada (Oi)", "Frec. Esperada (Ei)");
        System.out.println("-".repeat(63));
        for (int i = 0; i < result.getIntervalsCount(); i++) {
            double low = result.getIntervalEdges()[i];
            double high = result.getIntervalEdges()[i + 1];
            int obs = result.getObservedFrequencies()[i];
            double exp = result.getExpectedFrequencies()[i];
            String bracket = (i < result.getIntervalsCount() - 1) ? ")" : "]";
            String intervalStr = String.format(Locale.US, "[%.1f, %.1f%s", low, high, bracket);
            System.out.printf(Locale.US, "%-15s | %-22d | %-20.2f%n", intervalStr, obs, exp);
        }
        System.out.println("-".repeat(63));
        System.out.printf(Locale.US, "Estadístico Chi-cuadrado : %.6f%n", result.getChiSquareStat());
        System.out.printf("Grados de libertad (gl)  : %d%n", result.getDegreesOfFreedom());
        System.out.printf(Locale.US, "Valor p                  : %.6f%n", result.getPValue());
        System.out.printf(Locale.US, "Nivel de significancia   : %.2f%n", result.getAlpha());
        System.out.printf("Decisión                 : %s%n%n", result.getDecision());
    }

    public void showKsResults(KS result) {
        System.out.println("=== RESULTADOS PRUEBA KOLMOGOROV-SMIRNOV ===");
        System.out.printf("Cantidad de datos (N)    : %d%n", result.getN());
        System.out.printf(Locale.US, "Mayor dif. positiva (D+) : %.6f%n", result.getDPlusMax());
        System.out.printf(Locale.US, "Mayor dif. negativa (D-) : %.6f%n", result.getDMinusMax());
        System.out.printf(Locale.US, "Estadístico KS (D)       : %.6f%n", result.getKsStat());
        System.out.printf(Locale.US, "Valor p                  : %.6f%n", result.getPValue());
        System.out.printf(Locale.US, "Nivel de significancia   : %.2f%n", result.getAlpha());
        System.out.printf("Decisión                 : %s%n%n", result.getDecision());
    }
}