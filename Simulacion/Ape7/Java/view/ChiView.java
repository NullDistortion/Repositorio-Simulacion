package view;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import model.Escenario;

public class ChiView {

    public void mostrarPruebaChi(List<Escenario> escenarios) {
        System.out.println("=== PRUEBA DE UNIFORMIDAD CHI-CUADRADO (alfa = 0.05) ===");
        String encabezado = String.format("%10s | %13s | %4s | %10s | %10s | %12s",
                "N Puntos", "Muestras (2N)", "gl", "Chi Calc", "Chi Tabla", "Resultado");
        System.out.println(encabezado);
        System.out.println("-".repeat(encabezado.length()));

        for (Escenario e : escenarios) {
            int gl = e.getIntervalosK() - 1;
            String estado = e.isCumpleUniformidad() ? "ACEPTADO" : "RECHAZADO";
            System.out.printf(Locale.US, "%10d | %13d | %4d | %10.4f | %10.4f | %12s%n",
                    e.getNPuntos(), e.getTotalNumerosGenerados(), gl,
                    e.getChiCalculado(), e.getChiCritico(), estado);
        }
        System.out.println();
    }

    public void graficarChiCuadrado(List<Escenario> escenarios) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pruebas Estadisticas Chi-cuadrado (Java)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 800);
            frame.setLayout(new GridLayout(2, 2, 10, 10));

            for (Escenario e : escenarios) {
                frame.add(new PanelGraficoChi(e));
            }

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static class PanelGraficoChi extends JPanel {
        private final Escenario escenario;
        private static final double X_MAX = 30.0;

        public PanelGraficoChi(Escenario escenario) {
            this.escenario = escenario;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }

        // Función Gamma exacta para enteros y semienteros (k/2)
        private double gammaMitad(int gl) {
            if (gl % 2 == 0) {
                double fact = 1.0;
                for (int i = 1; i < (gl / 2); i++) {
                    fact *= i;
                }
                return fact;
            } else {
                double prod = Math.sqrt(Math.PI);
                for (int i = 0; i < (gl - 1) / 2; i++) {
                    prod *= (i + 0.5);
                }
                return prod;
            }
        }

        private double pdfChi(double x, int gl) {
            if (x <= 0) return 0.0;
            double kMedios = gl / 2.0;
            double num = Math.pow(x, kMedios - 1.0) * Math.exp(-x / 2.0);
            double den = Math.pow(2.0, kMedios) * gammaMitad(gl);
            return num / den;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int padL = 55, padR = 25, padT = 40, padB = 45;
            int plotW = w - padL - padR;
            int plotH = h - padT - padB;

            int gl = escenario.getIntervalosK() - 1;
            double maxY = 0.14;

            Path2D.Double curva = new Path2D.Double();
            Path2D.Double regionRechazo = new Path2D.Double();
            boolean rechazoIniciado = false;

            int pasos = 300;
            for (int i = 0; i <= pasos; i++) {
                double xVal = (i / (double) pasos) * X_MAX;
                double yVal = pdfChi(xVal, gl);
                double px = padL + (xVal / X_MAX) * plotW;
                double py = padT + plotH - (yVal / maxY) * plotH;

                if (i == 0) {
                    curva.moveTo(px, py);
                } else {
                    curva.lineTo(px, py);
                }

                if (xVal >= escenario.getChiCritico()) {
                    if (!rechazoIniciado) {
                        regionRechazo.moveTo(px, padT + plotH);
                        rechazoIniciado = true;
                    }
                    regionRechazo.lineTo(px, py);
                }
            }

            if (rechazoIniciado) {
                regionRechazo.lineTo(padL + plotW, padT + plotH);
                regionRechazo.closePath();
                g2.setColor(new Color(43, 92, 143, 200));
                g2.fill(regionRechazo);
            }

            // Ejes
            g2.setColor(Color.BLACK);
            g2.drawLine(padL, padT + plotH, padL + plotW, padT + plotH);
            g2.drawLine(padL, padT, padL, padT + plotH);

            // Curva PDF
            g2.setColor(new Color(200, 20, 20));
            g2.setStroke(new BasicStroke(2.0f));
            g2.draw(curva);

            // Línea crítica
            double pxCrit = padL + (escenario.getChiCritico() / X_MAX) * plotW;
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
            g2.drawLine((int) pxCrit, padT, (int) pxCrit, padT + plotH);

            // Punto Chi calculado
            double xCalc = Math.min(escenario.getChiCalculado(), X_MAX);
            double yCalc = pdfChi(xCalc, gl);
            double pxCalc = padL + (xCalc / X_MAX) * plotW;
            double pyCalc = padT + plotH - (yCalc / maxY) * plotH;

            g2.setColor(new Color(0, 140, 0));
            g2.fillOval((int) pxCalc - 5, (int) pyCalc - 5, 10, 10);

            // Etiquetas
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            g2.drawString(String.format("Muestra N = %d (Datos: %d)",
                    escenario.getNPuntos(), escenario.getTotalNumerosGenerados()), padL + 10, 22);

            g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
            g2.drawString(String.format(Locale.US, "Chi Calc = %.4f", escenario.getChiCalculado()), (int) pxCalc - 25, (int) pyCalc - 10);
            g2.drawString(String.format(Locale.US, "Chi Crit = %.2f", escenario.getChiCritico()), (int) pxCrit - 30, padT + plotH + 18);
            g2.drawString("Region Aceptacion H0", padL + 35, padT + plotH - 40);
            g2.drawString("Region Rechazo H0", (int) pxCrit + 10, padT + plotH - 40);
        }
    }
}