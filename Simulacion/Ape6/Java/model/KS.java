package Java.model;

public class KS {
    private final int n;
    private final double[] sortedNumbers;
    private final double[] empiricalCdf;
    private final double[] theoreticalCdf;
    private final double[] diffPositive;
    private final double[] diffNegative;
    private final double dPlusMax;
    private final double dMinusMax;
    private final double ksStat;
    private final double pValue;
    private final double alpha;
    private final boolean rejectH0;
    private final String decision;

    public KS(int n, double[] sortedNumbers, double[] empiricalCdf, double[] theoreticalCdf,
              double[] diffPositive, double[] diffNegative, double dPlusMax, double dMinusMax,
              double ksStat, double pValue, double alpha, boolean rejectH0, String decision) {
        this.n = n;
        this.sortedNumbers = sortedNumbers;
        this.empiricalCdf = empiricalCdf;
        this.theoreticalCdf = theoreticalCdf;
        this.diffPositive = diffPositive;
        this.diffNegative = diffNegative;
        this.dPlusMax = dPlusMax;
        this.dMinusMax = dMinusMax;
        this.ksStat = ksStat;
        this.pValue = pValue;
        this.alpha = alpha;
        this.rejectH0 = rejectH0;
        this.decision = decision;
    }

    public int getN() { return n; }
    public double[] getSortedNumbers() { return sortedNumbers; }
    public double[] getEmpiricalCdf() { return empiricalCdf; }
    public double[] getTheoreticalCdf() { return theoreticalCdf; }
    public double[] getDiffPositive() { return diffPositive; }
    public double[] getDiffNegative() { return diffNegative; }
    public double getDPlusMax() { return dPlusMax; }
    public double getDMinusMax() { return dMinusMax; }
    public double getKsStat() { return ksStat; }
    public double getPValue() { return pValue; }
    public double getAlpha() { return alpha; }
    public boolean isRejectH0() { return rejectH0; }
    public String getDecision() { return decision; }
}