package Java.service;

import Java.model.KS;
import java.util.Arrays;

public class AnalyzerKS {
    private final double alpha;

    public AnalyzerKS(double alpha) {
        this.alpha = alpha;
    }

    private double kolmogorovPValue(double dStat, int n) {
        if (dStat <= 0.0) {
            return 1.0;
        }
        double z = dStat * Math.sqrt(n);
        double total = 0.0;
        for (int k = 1; k < 1000; k++) {
            double sign = (k % 2 == 1) ? 1.0 : -1.0;
            double term = sign * Math.exp(-2.0 * k * k * z * z);
            total += term;
            if (Math.abs(term) < 1e-12) {
                break;
            }
        }
        double pVal = 2.0 * total;
        return Math.min(1.0, Math.max(0.0, pVal));
    }

    public KS analyze(double[] numbers) {
        double[] sortedNums = Arrays.copyOf(numbers, numbers.length);
        Arrays.sort(sortedNums);
        int n = sortedNums.length;
        double[] empiricalCdf = new double[n];
        double[] theoreticalCdf = new double[n];
        double[] diffPos = new double[n];
        double[] diffNeg = new double[n];

        double dPlusMax = 0.0;
        double dMinusMax = 0.0;

        for (int idx = 0; idx < n; idx++) {
            double x = sortedNums[idx];
            int pos = idx + 1;
            double fnX = (double) pos / n;
            double fX = x;

            double dPlus = fnX - fX;
            double dMinus = fX - ((double) idx / n);

            empiricalCdf[idx] = fnX;
            theoreticalCdf[idx] = fX;
            diffPos[idx] = dPlus;
            diffNeg[idx] = dMinus;

            if (dPlus > dPlusMax) dPlusMax = dPlus;
            if (dMinus > dMinusMax) dMinusMax = dMinus;
        }

        double ksStat = Math.max(dPlusMax, dMinusMax);

        double pVal = kolmogorovPValue(ksStat, n);

        boolean reject = pVal < this.alpha;
        String decision = !reject
                ? "No se rechaza la hipótesis nula (H0: X ~ U(0,1))."
                : "Se rechaza la hipótesis nula (H0: X ~ U(0,1)).";

        return new KS(n, sortedNums, empiricalCdf, theoreticalCdf, diffPos, diffNeg,
                dPlusMax, dMinusMax, ksStat, pVal, this.alpha, reject, decision);
    }
}