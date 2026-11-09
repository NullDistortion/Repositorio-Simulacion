package Java.numberTreatment;

public class NumberGenerator {
    private final long seed;
    private final long multiplier;
    private final long increment;
    private final long modulus;
    private long currentValue;

    public NumberGenerator(long seed, long multiplier, long increment, long modulus) {
        if (modulus <= 0) {
            throw new IllegalArgumentException("El módulo debe ser mayor que cero.");
        }
        this.seed = seed;
        this.multiplier = multiplier;
        this.increment = increment;
        this.modulus = modulus;
        this.currentValue = seed;
    }

    public double nextNumber() {
        long nextVal = (this.multiplier * this.currentValue + this.increment) % this.modulus;
        this.currentValue = nextVal;
        return (double) nextVal / this.modulus;
    }

    public void reset() {
        this.currentValue = this.seed;
    }
}