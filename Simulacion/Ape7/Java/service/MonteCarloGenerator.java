package service;

public class MonteCarloGenerator {
    private static final long M = 4294967296L; // 2^32
    private static final long A = 1664525L;
    private static final long C = 1013904223L;

    private final long semillaInicial;
    private long estado;

    public MonteCarloGenerator(long semilla) {
        this.semillaInicial = semilla;
        this.estado = semilla & 0xFFFFFFFFL;
    }

    public void reiniciar() {
        this.estado = this.semillaInicial & 0xFFFFFFFFL;
    }

    public double siguienteUniforme() {
        this.estado = (A * this.estado + C) % M;
        return (double) this.estado / M;
    }
}