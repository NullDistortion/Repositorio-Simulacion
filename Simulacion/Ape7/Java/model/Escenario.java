package model;

public class Escenario {
    private final int nPuntos;
    private final int puntosDentro;
    private final double piEstimado;
    private final double errorAbsoluto;
    private final double tiempoJava;
    private final int totalNumerosGenerados;
    private final int intervalosK;
    private final int[] frecuenciasObservadas;
    private final double frecuenciaEsperada;
    private final double chiCalculado;
    private final double chiCritico;
    private final boolean cumpleUniformidad;

    public Escenario(int nPuntos, int puntosDentro, double piEstimado, double errorAbsoluto,
                     double tiempoJava, int totalNumerosGenerados, int intervalosK,
                     int[] frecuenciasObservadas, double frecuenciaEsperada,
                     double chiCalculado, double chiCritico, boolean cumpleUniformidad) {
        this.nPuntos = nPuntos;
        this.puntosDentro = puntosDentro;
        this.piEstimado = piEstimado;
        this.errorAbsoluto = errorAbsoluto;
        this.tiempoJava = tiempoJava;
        this.totalNumerosGenerados = totalNumerosGenerados;
        this.intervalosK = intervalosK;
        this.frecuenciasObservadas = frecuenciasObservadas;
        this.frecuenciaEsperada = frecuenciaEsperada;
        this.chiCalculado = chiCalculado;
        this.chiCritico = chiCritico;
        this.cumpleUniformidad = cumpleUniformidad;
    }

    public int getNPuntos() { return nPuntos; }
    public int getPuntosDentro() { return puntosDentro; }
    public double getPiEstimado() { return piEstimado; }
    public double getErrorAbsoluto() { return errorAbsoluto; }
    public double getTiempoJava() { return tiempoJava; }
    public int getTotalNumerosGenerados() { return totalNumerosGenerados; }
    public int getIntervalosK() { return intervalosK; }
    public int[] getFrecuenciasObservadas() { return frecuenciasObservadas; }
    public double getFrecuenciaEsperada() { return frecuenciaEsperada; }
    public double getChiCalculado() { return chiCalculado; }
    public double getChiCritico() { return chiCritico; }
    public boolean isCumpleUniformidad() { return cumpleUniformidad; }
}