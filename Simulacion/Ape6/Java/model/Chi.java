package Java.model;

public class Chi {
    private final int intervalsCount;
    private final double[] intervalEdges;
    private final int[] observedFrequencies;
    private final double[] expectedFrequencies;
    private final double chiSquareStat;
    private final int degreesOfFreedom;
    private final double pValue;
    private final double alpha;
    private final boolean rejectH0;
    private final String decision;

    public Chi(int intervalsCount, double[] intervalEdges, int[] observedFrequencies,
               double[] expectedFrequencies, double chiSquareStat, int degreesOfFreedom,
               double pValue, double alpha, boolean rejectH0, String decision) {
        this.intervalsCount = intervalsCount;
        this.intervalEdges = intervalEdges;
        this.observedFrequencies = observedFrequencies;
        this.expectedFrequencies = expectedFrequencies;
        this.chiSquareStat = chiSquareStat;
        this.degreesOfFreedom = degreesOfFreedom;
        this.pValue = pValue;
        this.alpha = alpha;
        this.rejectH0 = rejectH0;
        this.decision = decision;
    }

    public int getIntervalsCount() { return intervalsCount; }
    public double[] getIntervalEdges() { return intervalEdges; }
    public int[] getObservedFrequencies() { return observedFrequencies; }
    public double[] getExpectedFrequencies() { return expectedFrequencies; }
    public double getChiSquareStat() { return chiSquareStat; }
    public int getDegreesOfFreedom() { return degreesOfFreedom; }
    public double getPValue() { return pValue; }
    public double getAlpha() { return alpha; }
    public boolean isRejectH0() { return rejectH0; }
    public String getDecision() { return decision; }
}