package generator;

import java.util.ArrayList;
import java.util.List;
import model.Model.LCGScenario;

public class NumberGenerator {

    public static class GeneratedSequences {
        private final List<Long> xValues;
        private final List<Double> uValues;

        public GeneratedSequences(List<Long> xValues, List<Double> uValues) {
            this.xValues = xValues;
            this.uValues = uValues;
        }

        public List<Long> getXValues() { return xValues; }
        public List<Double> getUValues() { return uValues; }
    }

    public static GeneratedSequences generate(LCGScenario scenario) {
        int n = scenario.getNValues();
        List<Long> xValues = new ArrayList<>(n);
        List<Double> uValues = new ArrayList<>(n);

        long xCurrent = scenario.getSeed();
        long a = scenario.getA();
        long c = scenario.getC();
        long m = scenario.getM();

        for (int i = 0; i < n; i++) {
            xCurrent = (a * xCurrent + c) % m;
            double uCurrent = (double) xCurrent / m;
            xValues.add(xCurrent);
            uValues.add(uCurrent);
        }

        return new GeneratedSequences(xValues, uValues);
    }
}