package timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Escenario;

public class TimeAnalyze {
    private double tiempoTotal;
    private double tiempoPromedio;
    private Map<Integer, Double> porcentajePorEscenario;

    public void analizarTiempos(List<Escenario> escenarios) {
        this.tiempoTotal = 0.0;
        this.porcentajePorEscenario = new HashMap<>();

        for (Escenario e : escenarios) {
            this.tiempoTotal += e.getTiempoJava();
        }

        this.tiempoPromedio = escenarios.isEmpty() ? 0.0 : this.tiempoTotal / escenarios.size();

        for (Escenario e : escenarios) {
            double pct = (this.tiempoTotal > 0) ? (e.getTiempoJava() / this.tiempoTotal) * 100.0 : 0.0;
            this.porcentajePorEscenario.put(e.getNPuntos(), pct);
        }
    }

    public double getTiempoTotal() {
        return tiempoTotal;
    }

    public double getTiempoPromedio() {
        return tiempoPromedio;
    }

    public Map<Integer, Double> getPorcentajePorEscenario() {
        return porcentajePorEscenario;
    }
}