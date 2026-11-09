package Java.numberTreatment;

public class NumberArrayer {
    private final NumberGenerator generator;
    private final int count;

    public NumberArrayer(NumberGenerator generator, int count) {
        this.generator = generator;
        this.count = count;
    }

    public double[] generateArray() {
        this.generator.reset();
        double[] numbers = new double[this.count];
        for (int i = 0; i < this.count; i++) {
            numbers[i] = this.generator.nextNumber();
        }
        return numbers;
    }
}