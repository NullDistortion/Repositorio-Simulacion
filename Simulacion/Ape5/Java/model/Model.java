package model;

import java.util.List;

public class Model {

    public static class LCGScenario {
        private final String name;
        private final long a;
        private final long c;
        private final long m;
        private final long seed;
        private final int nValues;

        public LCGScenario(String name, long a, long c, long m, long seed, int nValues) {
            this.name = name;
            this.a = a;
            this.c = c;
            this.m = m;
            this.seed = seed;
            this.nValues = nValues;
        }

        public String getName() { return name; }
        public long getA() { return a; }
        public long getC() { return c; }
        public long getM() { return m; }
        public long getSeed() { return seed; }
        public int getNValues() { return nValues; }
    }

    public static class PeriodAnalysisResult {
        private final int sampleSize;
        private final int uniqueValues;
        private final boolean empiricalPeriodFound;
        private final Integer firstRepeatIndex;
        private final Integer cycleLength;
        private final boolean hullDobellFullPeriod;
        private final long theoreticalPeriod;

        public PeriodAnalysisResult(
                int sampleSize,
                int uniqueValues,
                boolean empiricalPeriodFound,
                Integer firstRepeatIndex,
                Integer cycleLength,
                boolean hullDobellFullPeriod,
                long theoreticalPeriod) {
            this.sampleSize = sampleSize;
            this.uniqueValues = uniqueValues;
            this.empiricalPeriodFound = empiricalPeriodFound;
            this.firstRepeatIndex = firstRepeatIndex;
            this.cycleLength = cycleLength;
            this.hullDobellFullPeriod = hullDobellFullPeriod;
            this.theoreticalPeriod = theoreticalPeriod;
        }

        public int getSampleSize() { return sampleSize; }
        public int getUniqueValues() { return uniqueValues; }
        public boolean isEmpiricalPeriodFound() { return empiricalPeriodFound; }
        public Integer getFirstRepeatIndex() { return firstRepeatIndex; }
        public Integer getCycleLength() { return cycleLength; }
        public boolean isHullDobellFullPeriod() { return hullDobellFullPeriod; }
        public long getTheoreticalPeriod() { return theoreticalPeriod; }
    }

    public static class SimulationRun {
        private final LCGScenario scenario;
        private final List<Long> xValues;
        private final List<Double> uValues;
        private final PeriodAnalysisResult analysis;

        public SimulationRun(
                LCGScenario scenario,
                List<Long> xValues,
                List<Double> uValues,
                PeriodAnalysisResult analysis) {
            this.scenario = scenario;
            this.xValues = xValues;
            this.uValues = uValues;
            this.analysis = analysis;
        }

        public LCGScenario getScenario() { return scenario; }
        public List<Long> getXValues() { return xValues; }
        public List<Double> getUValues() { return uValues; }
        public PeriodAnalysisResult getAnalysis() { return analysis; }
    }
}