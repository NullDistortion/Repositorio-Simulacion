package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import model.Model.LCGScenario;
import model.Model.PeriodAnalysisResult;

public class Analyzer {

    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static List<Long> primeFactors(long n) {
        List<Long> factors = new ArrayList<>();
        if (n % 2 == 0) {
            factors.add(2L);
            while (n % 2 == 0) {
                n /= 2;
            }
        }
        for (long d = 3; d * d <= n; d += 2) {
            if (n % d == 0) {
                factors.add(d);
                while (n % d == 0) {
                    n /= d;
                }
            }
        }
        if (n > 1) {
            factors.add(n);
        }
        return factors;
    }

    public static boolean checkHullDobell(long a, long c, long m) {
        if (c == 0) {
            return false;
        }
        if (gcd(c, m) != 1) {
            return false;
        }
        for (long p : primeFactors(m)) {
            if ((a - 1) % p != 0) {
                return false;
            }
        }
        if (m % 4 == 0 && (a - 1) % 4 != 0) {
            return false;
        }
        return true;
    }

    public static PeriodAnalysisResult analyzePeriod(LCGScenario scenario, List<Long> xValues) {
        Map<Long, Integer> seenIndices = new HashMap<>();
        seenIndices.put(scenario.getSeed(), 0);

        Integer firstRepeatIdx = null;
        Integer cycleLen = null;

        for (int i = 0; i < xValues.size(); i++) {
            long val = xValues.get(i);
            int currentIdx = i + 1;
            if (seenIndices.containsKey(val)) {
                firstRepeatIdx = currentIdx;
                cycleLen = currentIdx - seenIndices.get(val);
                break;
            }
            seenIndices.put(val, currentIdx);
        }

        boolean fullPeriod = checkHullDobell(scenario.getA(), scenario.getC(), scenario.getM());
        int uniqueCount = new HashSet<>(xValues).size();

        return new PeriodAnalysisResult(
                xValues.size(),
                uniqueCount,
                firstRepeatIdx != null,
                firstRepeatIdx,
                cycleLen,
                fullPeriod,
                fullPeriod ? scenario.getM() : -1L
        );
    }
}