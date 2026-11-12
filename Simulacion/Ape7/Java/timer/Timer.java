package timer;

public class Timer {
    private long inicio;
    private long fin;

    public void iniciar() {
        this.inicio = System.nanoTime();
    }

    public double detener() {
        this.fin = System.nanoTime();
        return obtenerDuracion();
    }

    public double obtenerDuracion() {
        return (this.fin - this.inicio) / 1_000_000_000.0;
    }
}