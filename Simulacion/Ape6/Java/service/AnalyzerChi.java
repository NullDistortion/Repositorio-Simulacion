package Java.service;

import Java.model.Chi;
import java.util.Arrays;

public class AnalyzerChi {
    private final int intervals;
    private final double alpha;

    public AnalyzerChi(int intervals, double alpha) {
        this.intervals = intervals;
        this.alpha = alpha;
    }

    public static double logGammaHalf(int df) {
        double result = (df % 2 == 1) ? 0.5 * Math.log(Math.PI) : 0.0;
        for (double val = df / 2.0 - 1.0; val > 0.0; val -= 1.0) {
            result += Math.log(val);
        }
        return result;
    }

    public static double regularizedUpperGamma(int df, double x) {
        if (x <= 0.0) {
            return 1.0;
        }
        double a = df / 2.0;
        double term = 1.0 / a;
        double total = term;
        for (int n = 1; n < 1000; n++) {
            term *= x / (a + n);
            total += term;
            if (Math.abs(term) < 1e-12 * Math.abs(total)) {
                break;
            }
        }
        double logP = a * Math.log(x) - x - logGammaHalf(df) + Math.log(total);
        double pLower = Math.min(1.0, Math.max(0.0, Math.exp(logP)));
        return 1.0 - pLower;
    }

    public Chi analyze(double[] numbers) {
        int n = numbers.length;
        int k = this.intervals;

        double[] edges = new double[k + 1];
        for (int i = 0; i <= k; i++) {
            edges[i] = (double) i / k;
        }

        double expectedFreq = (double) n / k;
        double[] expectedList = new double[k];
        Arrays.fill(expectedList, expectedFreq);

        int[] observedList = new int[k];
        for (double num : numbers) {
            int idx = (int) (num * k);
            if (idx >= k) {
                idx = k - 1;
            } else if (idx < 0) {
                idx = 0;
            }
            observedList[idx]++;
        }

        double chiSq = 0.0;
        for (int i = 0; i < k; i++) {
            double diff = observedList[i] - expectedList[i];
            chiSq += (diff * diff) / expectedList[i];
        }

        int df = k - 1;

        double pVal = regularizedUpperGamma(df, chiSq / 2.0);

        boolean reject = pVal < this.alpha;
        String decision = !reject
                ? "No se rechaza la hipótesis nula (H0: X ~ U(0,1))."
                : "Se rechaza la hipótesis nula (H0: X ~ U(0,1)).";

        return new Chi(k, edges, observedList, expectedList, chiSq, df, pVal, this.alpha, reject, decision);
    }
}